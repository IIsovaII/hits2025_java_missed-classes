package com.example.hits2025_java_missed_classes.dto;

import com.example.hits2025_java_missed_classes.model.MissRequestType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class GantMissRequestItemDto {
    @NotNull
    LocalDate startDate;
    @NotNull
    LocalDate endDate;
    @NotNull
    MissRequestType type;
}