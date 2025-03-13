package com.example.hits2025_java_missed_classes.exception.unauthorized;

import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.UnauthorizedException;

public class TokenExpiredException extends UnauthorizedException {
    public TokenExpiredException(String message) {
        super(message);
    }
}