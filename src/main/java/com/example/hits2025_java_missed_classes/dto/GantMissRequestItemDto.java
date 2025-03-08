package com.example.hits2025_java_missed_classes.dto;

import com.example.hits2025_java_missed_classes.model.MissRequestType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GantMissRequestItemDto {
    @NotNull
    LocalDateTime startDate;
    @NotNull
    LocalDateTime endDate;
    @NotNull
    MissRequestType type;
}