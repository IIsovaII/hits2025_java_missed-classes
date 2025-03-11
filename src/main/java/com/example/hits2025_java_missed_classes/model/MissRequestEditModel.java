package com.example.hits2025_java_missed_classes.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class MissRequestEditModel {
    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private MissRequestType type;

    @NotNull
    private MissRequestStatus status;
}
