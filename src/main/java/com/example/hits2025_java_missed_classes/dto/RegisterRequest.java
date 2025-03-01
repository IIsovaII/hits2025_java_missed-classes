package com.example.hits2025_java_missed_classes.dto;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
}