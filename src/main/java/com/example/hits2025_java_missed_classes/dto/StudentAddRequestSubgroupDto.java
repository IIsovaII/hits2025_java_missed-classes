package com.example.hits2025_java_missed_classes.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StudentAddRequestSubgroupDto {
    @NotNull
    private UUID userId;
    @NotBlank
    private String groupName;
    @NotNull
    private UUID subgroupId;
}
