package com.example.hits2025_java_missed_classes.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ConfirmationFileDto {
    UUID id;

    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    private LocalDate attachDate;
}