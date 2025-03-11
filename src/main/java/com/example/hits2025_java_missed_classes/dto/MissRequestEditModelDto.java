package com.example.hits2025_java_missed_classes.dto;

import com.example.hits2025_java_missed_classes.model.MissRequestStatus;
import com.example.hits2025_java_missed_classes.model.MissRequestType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MissRequestEditModelDto {
    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private MissRequestType type;

    @NotNull
    private MissRequestStatus status;
}