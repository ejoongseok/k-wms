package com.example.kwms.common;

public class BadRequestException extends RuntimeException {
    public BadRequestException(final String message) {
        super(message);
    }
}
