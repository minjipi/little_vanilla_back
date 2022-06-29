package com.minji.idusbackend.member;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class EmailCertRedisService {

    private final RedisTemplate redisTemplate;

    public void saveToken(String key, String email, long duration) {
        final ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        stringStringValueOperations.set(key, email, expireDuration); // redis set 명령어
    }

    public boolean checkToken(String key, String email) {
        final ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        final String value = stringStringValueOperations.get(key);

        if (email.equals(value)){
            return true;
        } else {
            return false;
        }
    }

}
