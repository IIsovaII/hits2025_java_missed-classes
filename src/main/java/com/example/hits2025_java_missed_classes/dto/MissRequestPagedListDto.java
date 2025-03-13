package com.example.hits2025_java_missed_classes.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class MissRequestPagedListDto {
    @NotNull
    PaginationDto paginationDto;

    @NotNull
    @Valid
    List<MissRequestDto> requests;
}
