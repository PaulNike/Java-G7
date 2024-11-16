package com.openfeign.openfeign.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openfeign.openfeign.aggregates.constants.Constants;
import com.openfeign.openfeign.aggregates.response.ResponseSunat;
import com.openfeign.openfeign.client.SunatClient;
import com.openfeign.openfeign.redis.RedisService;
import com.openfeign.openfeign.service.InfoSunatService;
import com.openfeign.openfeign.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InfoSunatServiceImpl implements InfoSunatService {

    private final RedisService redisService;
    private final SunatClient sunatClient;

    @Value("${token.api}")
    private String token;
    @Override
    public ResponseSunat getInfoSunat(String ruc) throws JsonProcessingException {
        ResponseSunat responseSunat = new ResponseSunat();
        //Logica de mi diagrama
        //Recupero info de Redis;
        String dataRedis = redisService.getDataFromRedis(
                Constants.REDIS_KEY_API_SUNAT+ruc
        );

        //validamos la info de redi
        if(Objects.nonNull(dataRedis)){
            responseSunat = Util.convertirDesdeString(dataRedis, ResponseSunat.class);
        }else {
            //No existe infor en redis, iremos al cliente sunat
            responseSunat = executeSunat(ruc);
            if(Objects.nonNull(responseSunat)){
                String dataParaRedis = Util.convertirAString(responseSunat);
                redisService.saveInRedis(
                        Constants.REDIS_KEY_API_SUNAT+ruc,
                        dataParaRedis,
                        Constants.REDIS_TTL);
                return responseSunat;
            }
        }
        return responseSunat;
    }

    private ResponseSunat executeSunat(String ruc){
        String tokenComplete = Constants.BEARER+token;
        return sunatClient.getInfoSunat(ruc,tokenComplete);
    }
}
