package com.example.hits2025_java_missed_classes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GroupDto {
    final private String name;
    final private List<SubgroupDto> subgroups;
}
