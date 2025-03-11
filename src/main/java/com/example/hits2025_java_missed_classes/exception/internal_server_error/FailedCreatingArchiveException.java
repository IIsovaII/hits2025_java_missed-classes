package com.example.hits2025_java_missed_classes.exception.internal_server_error;

import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.InternalServerErrorException;

public class FailedCreatingArchiveException extends InternalServerErrorException {
    public FailedCreatingArchiveException() {
        super("Failed to create archive");
    }
}
