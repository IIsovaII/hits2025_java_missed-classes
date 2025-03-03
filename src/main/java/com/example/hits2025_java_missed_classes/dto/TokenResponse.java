package com.example.hits2025_java_missed_classes.dto;

import lombok.Data;

@Data
public class TokenResponse {
    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }
}