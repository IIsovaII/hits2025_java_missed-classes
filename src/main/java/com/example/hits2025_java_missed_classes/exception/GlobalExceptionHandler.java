package com.example.hits2025_java_missed_classes.exception;

import com.example.hits2025_java_missed_classes.dto.ErrorResponseDto;
import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.BadRequestException;
import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.ForbiddenException;
import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.InternalServerErrorException;
import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponseDto handleInternalServerErrorException(EntityNotFoundException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError ->
                        fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        return new ErrorResponseDto(errors);
    }
}