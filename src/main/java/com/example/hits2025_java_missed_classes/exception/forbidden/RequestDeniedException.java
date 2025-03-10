package com.example.hits2025_java_missed_classes.exception.forbidden;

import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.ForbiddenException;

public class RequestDeniedException extends ForbiddenException {
    public RequestDeniedException(String message) {
        super(message);
    }
}