package com.example.hits2025_java_missed_classes.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class StudentAddRequest {
    private UUID userId;
    private String groupName;
    private UUID subGroupId;
}
