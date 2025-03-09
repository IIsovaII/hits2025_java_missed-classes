package com.example.hits2025_java_missed_classes.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
public class UserAssignToRoleModelDto {
    @NotNull
    private UUID userId;
}
