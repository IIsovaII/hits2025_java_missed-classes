package com.example.hits2025_java_missed_classes.service;


import com.example.hits2025_java_missed_classes.security.JwtTokenProvider;
import com.example.hits2025_java_missed_classes.security.TokenBlacklist;
import org.springframework.stereotype.Service;


@Service
public class BlacklistService {
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklist tokenBlacklist;

    public BlacklistService(JwtTokenProvider jwtTokenProvider, TokenBlacklist tokenBlacklist) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenBlacklist = tokenBlacklist;
    }

    // TODO: проверка что такой jwt уже не лежит в блеклисте - по необходимости
    // TODO: проверка что токен уже сам не просрочился - по необходимости
    // по необходимости, тк и так код выполняет тз, просто криво
    public void logoutUser(String token) {
        String jwtToken = token.substring(7);
        long expirationTime = jwtTokenProvider.getExpirationTimeFromToken(jwtToken);
        tokenBlacklist.addToBlacklist(jwtToken, expirationTime);
    }
}