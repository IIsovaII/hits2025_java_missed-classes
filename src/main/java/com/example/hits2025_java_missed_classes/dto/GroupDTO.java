package com.example.hits2025_java_missed_classes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GroupDTO {
    final private UUID name;
    final private List<SubGroupDTO> subGroups;
}
