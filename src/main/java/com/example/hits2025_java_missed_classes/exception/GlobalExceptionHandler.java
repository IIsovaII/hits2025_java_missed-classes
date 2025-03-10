package com.example.hits2025_java_missed_classes.exception;

import com.example.hits2025_java_missed_classes.dto.ErrorResponseDto;
import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.BadRequestException;
import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.ForbiddenException;
import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.InternalServerErrorException;
import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponseDto handleNotFoundException(NotFoundException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ErrorResponseDto handleBadRequestException(BadRequestException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ErrorResponseDto handleBadRequestException(ForbiddenException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ErrorResponseDto handleInternalServerErrorException(InternalServerErrorException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }
}