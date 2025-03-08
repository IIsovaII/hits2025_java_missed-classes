package com.example.hits2025_java_missed_classes.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class GantResponseDto {
    @NotNull
    List<GantGroupItemDto> groups;
    @NotNull
    Pagination pagination;
}