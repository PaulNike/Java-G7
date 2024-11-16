package com.codigo.restTemplate.service.impl;

import com.codigo.restTemplate.aggregates.constants.Constants;
import com.codigo.restTemplate.aggregates.response.ResponseSunat;
import com.codigo.restTemplate.redis.RedisService;
import com.codigo.restTemplate.service.InfoSunatService;
import com.codigo.restTemplate.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InfoSunatServiceImpl implements InfoSunatService {

    private final RedisService redisService;
    private final RestTemplate restTemplate;

    @Value("${token.api}")
    private String token;
    @Override
    public ResponseSunat getInfoSunat(String ruc) throws IOException {
        ResponseSunat responseSunat = new ResponseSunat();
        //Logica del diagrama:
        //Recuperamos la información de redis
        String sunatRedisInfo = redisService.getDataFromRedis(
                Constants.REDIS_KEY_API_SUNAT+ruc);
        //Validando Info de Redis
        if(Objects.nonNull(sunatRedisInfo)){
            //si existe info enr edis
            responseSunat = Util.convertirDesdeString(sunatRedisInfo,
                    ResponseSunat.class);
        } else{
            //NO EXISTE INFO EN REDIS, POR ENDE VAMOS A GOLEPAR AL CLIENTE EXTERNO
            responseSunat = executeRestTemplate(ruc);
            if (Objects.nonNull(responseSunat)){
                String dataParaRedis =
                        Util.convertirAString(responseSunat);
                redisService.saveInRedis(Constants.REDIS_KEY_API_SUNAT+ruc,
                        dataParaRedis,Constants.REDIS_TTL);
                return responseSunat;
            }
        }
        return responseSunat;
    }

    private ResponseSunat executeRestTemplate(String ruc){
        String urlComplete = Constants.BASE_URL+"/v2/sunat/ruc/full?numero="+ruc;

        ResponseEntity<ResponseSunat> execute =
                restTemplate.exchange(
                        urlComplete,
                        HttpMethod.GET,
                        new HttpEntity<>(createHeaders()),
                        ResponseSunat.class
                );
        if (execute.getStatusCode().equals(HttpStatus.OK)){
            return execute.getBody();
        }
        return null;
    }

    private HttpHeaders createHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",Constants.BEARER+token);
        return headers;
    }

}
