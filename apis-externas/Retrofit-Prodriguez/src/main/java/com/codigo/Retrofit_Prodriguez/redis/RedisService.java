package com.codigo.Retrofit_Prodriguez.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor //Generar un constructor con todos los campos definidos como FINAL
//@NoArgsConstructor //Genera un CONstructor sin argumentos osea vaciooooo
//@AllArgsConstructor  //Genera un constructor que incluye absolutamente todos los campos de la clase.
public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;
    public void saveInRedis(String key, String value, int expiration){
        stringRedisTemplate.opsForValue().set(key,value);
        stringRedisTemplate.expire(key,expiration, TimeUnit.MINUTES);
    }
    public String getDataFromRedis(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }
}
