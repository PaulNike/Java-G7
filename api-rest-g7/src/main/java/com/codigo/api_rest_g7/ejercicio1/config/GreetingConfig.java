/*package com.codigo.api_rest_g7.ejercicio1.config;

import com.codigo.api_rest_g7.ejercicio1.service.GreetingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class GreetingConfig {

    @Bean
    @Primary
    public GreetingService primaryGreetingService(){
        return () -> "Hola desde el primer servicio!";
    }

    @Bean
    public GreetingService secondaryGreetingService(){
        return () -> "Hola desde el segundo servicio!";
    }
}
*/