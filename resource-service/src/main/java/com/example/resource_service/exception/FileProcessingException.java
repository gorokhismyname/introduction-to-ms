package com.example.resource_service.exception;

import lombok.Getter;

@Getter
public class FileProcessingException extends RuntimeException {
    public FileProcessingException(String message) {
        super(message);
    }
}
