package com.example.song_service.exception;

public class InvalidCSVFormatException extends RuntimeException {
    public InvalidCSVFormatException(String message) {
        super(message);
    }
}
