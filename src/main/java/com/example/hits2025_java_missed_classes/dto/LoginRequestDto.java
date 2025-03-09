package com.example.hits2025_java_missed_classes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
    @Email
    private String email;
    @NotBlank
    private String password;
}
