package com.example.hits2025_java_missed_classes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GroupDto {
    @NotBlank
    final private String name;
    final private List<SubgroupDto> subgroups;
}
