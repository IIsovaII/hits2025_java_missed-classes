package com.example.hits2025_java_missed_classes.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class StudentDto {
    @NotNull
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String surname;

    private String patronymic;

    private List<String> groupNames;

    private List<String> subgroupNames;
}
