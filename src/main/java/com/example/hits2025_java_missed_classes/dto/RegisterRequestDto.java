package com.example.hits2025_java_missed_classes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDto {
    @NotNull
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private String surname;

    private String patronymic;

    @NotNull
    @Size(min =  5, max = 64)
    private String password;
}
