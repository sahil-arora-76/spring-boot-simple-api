package com.backend.api.api.Exception;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }
}
