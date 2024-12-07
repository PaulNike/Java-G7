package com.codigo.apigateway.filter;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class GlobalFilterConfiguration {

    @Component
    public class AuthenticationFilter implements GlobalFilter, Ordered{

        //Inyecciones
        @Autowired
        private WebClient.Builder webClientBuilder;

        @Autowired
        private EurekaClient eurekaClient;

        //Definir las rutas que van a ser excluidas
        private final AntPathMatcher antPathMatcher = new AntPathMatcher();
        //Rutas que se van a excluir de la validacion
        private static final List<String> EXCLUDE_PATHS = List.of(
                "/apis/codigo/api/authentication/v1/**"
        );

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            String path = exchange.getRequest().getURI().getPath();

            //Verificamos si la ruta o Path de la solicitud esta en la
            // lista de excluidos para la validacion
            if (isExcludePath(path)){
                return chain.filter(exchange);
            }

            //Obtener el token de la solicitud (Token debe llegar puro osea sin Bearer)
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");

            //Validamos qeu el token no sea nulo o vacio
            if(token == null || token.isEmpty()){
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            //Crear la URl completa usando al Eureka Server y el ENdPoint que se ejecutara
            String serviceUrl = getServiceUrl("ms-seguridad", "apis/codigo/api/authentication/v1/validateToken");

            //Ejecutamos el servicio de validacion
            return webClientBuilder.build()
                    .post()
                    .uri(serviceUrl)
                    .header("validate", token)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .flatMap(isValid -> {
                        if(Boolean.TRUE.equals(isValid)){
                            return chain.filter(exchange);
                        }else {
                            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                            return exchange.getResponse().setComplete();
                        }
                    })
                    .onErrorResume(erro -> {
                        exchange.getResponse().setStatusCode(HttpStatus.CONFLICT);
                        return exchange.getResponse().setComplete();
                    });

        }

        private String getServiceUrl(String serviceName, String endPoint) {

            InstanceInfo instanceInfo = eurekaClient
                    .getNextServerFromEureka(serviceName, false);
            return instanceInfo.getHomePageUrl()  + endPoint;

        }

        private boolean isExcludePath(String path) {
            return EXCLUDE_PATHS.stream().anyMatch(
                    pattern -> antPathMatcher.match(pattern,path));

        }

        @Override
        public int getOrder() {
            return 0; // Menor valor == Mayor prioridad || Mayor Valor == Menor Prioridad
        }
    }
}
