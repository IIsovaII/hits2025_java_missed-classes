package com.example.hits2025_java_missed_classes.dto;

import lombok.Data;

@Data
public class RegisterResponse {
    private String token;

    public RegisterResponse(String token) {
        this.token = token;
    }
}
