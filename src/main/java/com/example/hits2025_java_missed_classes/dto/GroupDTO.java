package com.example.hits2025_java_missed_classes.dto;

import lombok.Data;

import java.util.List;

@Data
public class GroupDTO {
    final private String groupName;
    final private List<SubGroupDTO> subGroups;
}
