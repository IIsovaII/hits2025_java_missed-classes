package com.example.hits2025_java_missed_classes.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String email;
    private String surname;
    private String patronymic;
    private String password;

    public RegisterRequest() {}
}
