package com.example.hits2025_java_missed_classes.exception;

public class RequestDeniedException extends RuntimeException {
    public RequestDeniedException(String message) {
        super(message);
    }
}