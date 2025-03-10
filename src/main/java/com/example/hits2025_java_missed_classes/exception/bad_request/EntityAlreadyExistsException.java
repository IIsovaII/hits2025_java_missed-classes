package com.example.hits2025_java_missed_classes.exception.bad_request;

import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.BadRequestException;

public class EntityAlreadyExistsException extends BadRequestException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}