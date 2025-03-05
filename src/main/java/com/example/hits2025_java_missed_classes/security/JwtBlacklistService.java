package com.example.hits2025_java_missed_classes.security;

import com.example.hits2025_java_missed_classes.model.JwtBlacklistEntity;
import com.example.hits2025_java_missed_classes.repository.JwtBlacklistRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class JwtBlacklistService {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final JwtBlacklistRepository jwtBlacklistRepository;

    public JwtBlacklistService(JwtBlacklistRepository jwtBlacklistRepository) {
        // Очистка устаревших токенов каждую минуту
        scheduler.scheduleAtFixedRate(this::cleanupExpiredTokens, 1, 1, TimeUnit.MINUTES);
        this.jwtBlacklistRepository = jwtBlacklistRepository;
    }

    public void addToBlacklist(String token, long expirationTime) {
        JwtBlacklistEntity entity = new JwtBlacklistEntity();
        entity.setToken(token);
        entity.setExpirationTime(expirationTime);
        jwtBlacklistRepository.save(entity);
    }

    public boolean isBlacklisted(String token) {
        return jwtBlacklistRepository.existsById(token);
    }

    private void cleanupExpiredTokens() {
        long now = System.currentTimeMillis();
        jwtBlacklistRepository.deleteByExpirationTimeLessThanEqual(now);
    }
}