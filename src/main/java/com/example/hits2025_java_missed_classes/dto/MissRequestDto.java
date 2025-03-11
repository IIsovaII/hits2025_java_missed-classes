package com.example.hits2025_java_missed_classes.dto;

import com.example.hits2025_java_missed_classes.model.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class MissRequestDto {
    @NotNull
    private UUID id;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private MissRequestType missRequestType;

    @NotNull
    private MissRequestStatus missRequestStatus;

    private List<ConfirmationFileDto> confirmationFiles;
}