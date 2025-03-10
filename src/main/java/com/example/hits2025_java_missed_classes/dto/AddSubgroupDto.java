package com.example.hits2025_java_missed_classes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddSubgroupDto {
    @NotNull
    SubgroupDto subgroup;
    @NotBlank
    String groupName;
}
