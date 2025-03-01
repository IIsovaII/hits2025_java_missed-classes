package com.example.hits2025_java_missed_classes.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    // сериализации/десериализации
    public AuthResponse() {
    }
}