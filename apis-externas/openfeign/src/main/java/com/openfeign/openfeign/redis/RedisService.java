package com.openfeign.openfeign.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
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
