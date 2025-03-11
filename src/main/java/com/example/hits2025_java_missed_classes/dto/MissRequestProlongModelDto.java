package com.example.hits2025_java_missed_classes.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MissRequestProlongModelDto {
    @NotNull
    LocalDate newEndDate;
}
