package com.example.hits2025_java_missed_classes.security;

import com.example.hits2025_java_missed_classes.model.TokenBlacklistEntity;
import com.example.hits2025_java_missed_classes.repository.TokenBlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class TokenBlacklist {

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public TokenBlacklist() {
        // Очистка устаревших токенов каждую минуту
        scheduler.scheduleAtFixedRate(this::cleanupExpiredTokens, 1, 1, TimeUnit.MINUTES);
    }

    public void addToBlacklist(String token, long expirationTime) {
        TokenBlacklistEntity entity = new TokenBlacklistEntity();
        entity.setToken(token);
        entity.setExpirationTime(expirationTime);
        tokenBlacklistRepository.save(entity);
    }

    public boolean isBlacklisted(String token) {
        return tokenBlacklistRepository.existsById(token);
    }

    private void cleanupExpiredTokens() {
        long now = System.currentTimeMillis();
        tokenBlacklistRepository.deleteByExpirationTimeLessThanEqual(now);
    }
}
