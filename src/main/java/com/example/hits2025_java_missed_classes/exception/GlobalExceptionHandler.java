package com.example.hits2025_java_missed_classes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RequestDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleRequestDeniedException(RequestDeniedException ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage()), HttpStatus.FORBIDDEN);
    }
}