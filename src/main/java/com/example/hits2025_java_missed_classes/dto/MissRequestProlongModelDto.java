package com.example.hits2025_java_missed_classes.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MissRequestProlongModelDto {
    @NotNull
    LocalDateTime newEndDate;
}
