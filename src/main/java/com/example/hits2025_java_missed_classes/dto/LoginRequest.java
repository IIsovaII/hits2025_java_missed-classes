package com.example.hits2025_java_missed_classes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginRequest {
//    @Schema(description = "User's email", example = "user@email.com")
//    @Email(regexp = ".+[@].+[\\.].+", message = "Email is not given pattern")
//    @NotBlank(message = "Email field can't be empty")
    private String email;
    private String password;

    public LoginRequest() {
    }
}
