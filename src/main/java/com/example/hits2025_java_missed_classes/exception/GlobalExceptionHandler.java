package com.example.hits2025_java_missed_classes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(RequestDeniedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(RequestDeniedException ex) {
        return ex.getMessage();
    }
}*/