package com.example.hits2025_java_missed_classes.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GantStudentItemDto {
    @NotNull
    String surname;
    @NotNull
    String name;
    String patronymic;
    List<GantMissRequestItemDto> requests;
}
