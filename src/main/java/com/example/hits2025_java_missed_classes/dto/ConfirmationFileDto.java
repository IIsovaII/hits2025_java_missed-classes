package com.example.hits2025_java_missed_classes.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ConfirmationFileDto {
    @NotBlank
    @Max(value = 256)
    private String name;

    @NotNull
    private LocalDate attachDate;
}