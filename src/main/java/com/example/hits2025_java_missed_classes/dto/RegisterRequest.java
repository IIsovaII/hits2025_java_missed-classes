package com.example.hits2025_java_missed_classes.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RegisterRequest {
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
    private Set<String> roles;

    public RegisterRequest() {}
}
