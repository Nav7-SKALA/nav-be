package com.skala.nav7.global.auth.email.service;

import com.skala.nav7.global.auth.email.error.EmailErrorCode;
import com.skala.nav7.global.auth.email.error.EmailException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {
    private static final int RANGE = 1000000;
    private static final String KEY = "email:code:";

    private final RedisTemplate<String, String> redisTemplate;

    public String generateCode(String email) {
        String code = String.format("%06d", new SecureRandom().nextInt(RANGE));
        String key = KEY + email;
        redisTemplate.opsForValue().set(key, code, 3, TimeUnit.MINUTES); //3분

        return code;
    }

    public boolean verifyCode(String email, String inputCode) {
        if (inputCode.isEmpty()) {
            throw new EmailException(EmailErrorCode.INVALID_EMAIL_CODE);
        }
        String key = KEY + email;
        String storedCode = redisTemplate.opsForValue().get(key);
        return inputCode.equals(storedCode);
    }
}
