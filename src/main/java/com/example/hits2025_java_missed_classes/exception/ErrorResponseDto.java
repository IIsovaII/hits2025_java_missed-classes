package com.example.hits2025_java_missed_classes.exception;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class ErrorResponseDto {
    private List<String> errors;

    public ErrorResponseDto(String error) {
        this.errors = Collections.singletonList(error);
    }

    public ErrorResponseDto(List<String> errors) {
        this.errors = errors;
    }
}