package com.example.hits2025_java_missed_classes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetSubGroupDto {
    @NotBlank
    String groupName;
    String subgroupName;
}
