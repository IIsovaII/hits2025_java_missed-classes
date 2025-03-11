package com.example.hits2025_java_missed_classes.dto;

import com.example.hits2025_java_missed_classes.model.MissRequestType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class MissRequestCreateModelDto {
    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    private MissRequestType type;

    @NotNull
    private List<ConfirmationFileCreateModelDto> confirmationFiles;
}
