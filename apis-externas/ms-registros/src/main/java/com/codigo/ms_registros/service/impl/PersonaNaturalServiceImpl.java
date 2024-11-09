package com.codigo.ms_registros.service.impl;

import com.codigo.ms_registros.aggregates.constants.Constants;
import com.codigo.ms_registros.aggregates.response.ResponseReniec;
import com.codigo.ms_registros.client.ClientReniec;
import com.codigo.ms_registros.entity.PersonaNaturalEntity;
import com.codigo.ms_registros.redis.RedisService;
import com.codigo.ms_registros.repository.PersonaNaturalRepository;
import com.codigo.ms_registros.retrofit.ClientReniecService;
import com.codigo.ms_registros.retrofit.impl.ClientReniecServiceImpl;
import com.codigo.ms_registros.service.PersonaNaturalService;
import com.codigo.ms_registros.util.Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Objects;

@Service
@Log4j2
public class PersonaNaturalServiceImpl implements PersonaNaturalService {

    private final PersonaNaturalRepository personaNaturalRepository;
    private final ClientReniec clientReniec;
    private final RestTemplate restTemplate;
    private final RedisService redisService;


    ClientReniecService reniecServiceRetrofit = ClientReniecServiceImpl
            .getRetrofit()
            .create(ClientReniecService.class);

    @Value("${token.api}")
    private String token;

    public PersonaNaturalServiceImpl(PersonaNaturalRepository personaNaturalRepository,
                                     ClientReniec clientReniec,
                                     RestTemplate restTemplate, RedisService redisService) {
        this.personaNaturalRepository = personaNaturalRepository;
        this.clientReniec = clientReniec;
        this.restTemplate = restTemplate;
        this.redisService = redisService;
    }

    @Override
    public PersonaNaturalEntity guardar(String dni) throws IOException{
        //PersonaNaturalEntity personaNatural = getEntity(dni); //Retrofit
        PersonaNaturalEntity personaNatural = getEntityForRestTemplate(dni);  //RestTemplate
        if(Objects.nonNull(personaNatural)){
            return personaNaturalRepository.save(personaNatural);
        }else {
            return null;
        }
    }

    @Override
    public ResponseReniec getInfoReniec(String dni) {
        ResponseReniec datosReniec = new ResponseReniec();
        //Recupero la Información de Redis
        String redisInfo = redisService.getDataFromRedis(Constants.REDIS_KEY_API_RENIEC+dni);
        //Valido que exista la info
        if(Objects.nonNull(redisInfo)){
            datosReniec = Util.convertirDesdeString(redisInfo, ResponseReniec.class);
            return datosReniec;
        }else{
            //Sino existe la data en redis me voy a Reniec api
            datosReniec = executeRestTemplate(dni);
            //Convertir a String para poder guardarlo en Redis
            String dataForRedis = Util.convertirAString(datosReniec);
            //Guardando en Redis la información
            redisService.saveInRedis(Constants.REDIS_KEY_API_RENIEC+dni,dataForRedis,Constants.REDIS_TTL);
            return datosReniec;
        }
    }

    private PersonaNaturalEntity getEntity(String dni) throws IOException {
        PersonaNaturalEntity personaNaturalEntity = new PersonaNaturalEntity();
        //Ejecuta a Reniec usando OpenFeign
        //ResponseReniec datosReniec = executionReniec(dni);

        //Preparo el objeto Reniec usando Retrofit:
        Call<ResponseReniec> clientRetrofit = prepareReniecRetrofit(dni);
        log.info("getEntity -> Se Preparo todo el cliente Retrofit, listo para ejecutar!");
        //Ejecuto a Reniec usando Retrofit:
        Response<ResponseReniec> executeReniec = clientRetrofit.execute();
        log.info("getEntity -> CLiente Retrofit Ejecutado");

        //Validar el resultado
        ResponseReniec datosReniec = null;
        if (executeReniec.isSuccessful() && Objects.nonNull(executeReniec.body())){
            datosReniec = executeReniec.body();
            log.error("getEntity -> valores del body: " + executeReniec.body().toString());
        }

        if(Objects.nonNull(datosReniec)){
            personaNaturalEntity.setNombres(datosReniec.getNombres());
            personaNaturalEntity.setApellidoPaterno(datosReniec.getApellidoPaterno());
            personaNaturalEntity.setApellidoMaterno(datosReniec.getApellidoMaterno());
            personaNaturalEntity.setNumeroDocumento(datosReniec.getNumeroDocumento());
            personaNaturalEntity.setTipoDocumento(datosReniec.getTipoDocumento());
            personaNaturalEntity.setDigitoVerificador(datosReniec.getDigitoVerificador());
            personaNaturalEntity.setEstado(Constants.ESTADO_ACTVO);
            personaNaturalEntity.setUserCreated(Constants.USER_CREATED);
            personaNaturalEntity.setDateCreated(new Timestamp(System.currentTimeMillis()));
        }
        return personaNaturalEntity;
    }

    private PersonaNaturalEntity getEntityForRestTemplate(String dni) throws IOException {
        PersonaNaturalEntity personaNaturalEntity = new PersonaNaturalEntity();
        ResponseReniec datosReniec = new ResponseReniec();
        //Recupero la Información de Redis
        String redisInfo = redisService.getDataFromRedis(dni);
        //Valido que exista la info
        if(Objects.nonNull(redisInfo)){
            datosReniec = Util.convertirDesdeString(redisInfo, ResponseReniec.class);
        }else{
            //Sino existe la data en redis me voy a Reniec api
            datosReniec = executeRestTemplate(dni);
            //Convertir a String para poder guardarlo en Redis
            String dataForRedis = Util.convertirAString(datosReniec);
            //Guardando en Redis la información
            redisService.saveInRedis(dni,dataForRedis,Constants.REDIS_TTL);
        }
        //Validar el resultado
        if(Objects.nonNull(datosReniec)){
            personaNaturalEntity.setNombres(datosReniec.getNombres());
            personaNaturalEntity.setApellidoPaterno(datosReniec.getApellidoPaterno());
            personaNaturalEntity.setApellidoMaterno(datosReniec.getApellidoMaterno());
            personaNaturalEntity.setNumeroDocumento(datosReniec.getNumeroDocumento());
            personaNaturalEntity.setTipoDocumento(datosReniec.getTipoDocumento());
            personaNaturalEntity.setDigitoVerificador(datosReniec.getDigitoVerificador());
            personaNaturalEntity.setEstado(Constants.ESTADO_ACTVO);
            personaNaturalEntity.setUserCreated(Constants.USER_CREATED);
            personaNaturalEntity.setDateCreated(new Timestamp(System.currentTimeMillis()));
        }
        return personaNaturalEntity;
    }

    //Metodo que ejecuta el client OpenFeign de Reniec
    private ResponseReniec executionReniec(String dni){
        //String tokenOk = "Bearer "+token;
        String tokenOk = Constants.BEARER+token;
        return clientReniec.getPersonaReniec(dni,tokenOk);
    }

    //Metodo que prepara el client Retrofit de Reniec
    private Call<ResponseReniec> prepareReniecRetrofit(
            String dni){
        String tokenComplete = "Bearer "+token;
        log.info("prepareReniecRetrofit -> Ejecutando Metodo de Apoyo que crea el objeto retrofit completo");
        return reniecServiceRetrofit.getInfoReniec(tokenComplete,dni);
    }

    //ASINCRONA ->  eS CUANDO EJECUTAS UNSA SOLICITUD PERO NO TIENES RESPUESTA
    //SINCRONA -> Es cuadno tu ejecutas una solicitus y esperas una respuesta.

    private ResponseReniec executeRestTemplate(String dni){
        //Configurar una URL completa como String
        String url = "https://api.apis.net.pe/v2/reniec/dni?numero="+dni;
        //Genero mi CLient RestTemplate y Ejecuto
        ResponseEntity<ResponseReniec> executeRestTemplate =
                restTemplate.exchange(
                url, //URL A LA CUAL VAS A EJECUTAR
                HttpMethod.GET, //TIPO DE SOLICITUD AL QUE PERTENCE LA URL
                new HttpEntity<>(createHeaders()), //CABECERAS || HEADERS
                ResponseReniec.class // RESPONSE A CASTEAR
        );

        if(executeRestTemplate.getStatusCode().equals(HttpStatus.OK)){
            return executeRestTemplate.getBody();
        }else {
            return null;
        }
    }

    private HttpHeaders createHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer "+token);
        return headers;
    }
}
