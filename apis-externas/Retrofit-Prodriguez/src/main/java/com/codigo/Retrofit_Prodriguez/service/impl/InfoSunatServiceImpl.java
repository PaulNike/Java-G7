package com.codigo.Retrofit_Prodriguez.service.impl;

import com.codigo.Retrofit_Prodriguez.aggregates.constants.Constants;
import com.codigo.Retrofit_Prodriguez.aggregates.response.ResponseSunat;
import com.codigo.Retrofit_Prodriguez.redis.RedisService;
import com.codigo.Retrofit_Prodriguez.retrofit.ClientSunatService;
import com.codigo.Retrofit_Prodriguez.retrofit.impl.ClientSunatServiceImpl;
import com.codigo.Retrofit_Prodriguez.service.InfoSunatService;
import com.codigo.Retrofit_Prodriguez.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InfoSunatServiceImpl implements InfoSunatService {

    private final RedisService redisService;

    //Definición una instancia de retrofit que se pueda usar
    ClientSunatService clientSunatService = ClientSunatServiceImpl
            .getRetrofitSunat()
            .create(ClientSunatService.class);

    @Value("${token.api}")
    private String token;
    @Override
    public ResponseSunat getInfoSunat(String ruc) throws IOException {
        ResponseSunat responseSunat = new ResponseSunat();
        //Logica de mi diagrama
        //Recupero la información de Redis
        String sunatRedisInfo = redisService.getDataFromRedis(
                Constants.REDIS_KEY_API_SUNAT+ruc);
        //Validando que exista info o no
        if(Objects.nonNull(sunatRedisInfo)){
            //Si existe info en Redis
            responseSunat = Util.convertirDesdeString(sunatRedisInfo, ResponseSunat.class);
        }else{
            //No existe infor en redis, iremos al cliente sunat
            //Ejecutar clienteSunat Retrofit
            Response<ResponseSunat> executeSunat = preparacionClientSunat(ruc).execute();
            //Validar que responde el api
            if(executeSunat.isSuccessful() && Objects.nonNull(executeSunat.body())){
                //Recupero EL body (Solo el cuerpo porque alli tengo la infromación que requiero)
                responseSunat = executeSunat.body();
                //Creo mi String para guardar en redis
                String dataParaRedis = Util.convertirAString(responseSunat);
                redisService.saveInRedis(Constants.REDIS_KEY_API_SUNAT+ruc,
                        dataParaRedis,
                        Constants.REDIS_TTL);
            }
        }
        return responseSunat;
    }

    private Call<ResponseSunat> preparacionClientSunat(String ruc){
        return clientSunatService.getInfoSunat(Constants.BEARER+token,ruc);
    }
}
