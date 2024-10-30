package com.example.song_service.exception;

import lombok.Getter;

@Getter
public class MetadataFieldValidationException extends RuntimeException {
    private String label;
    public MetadataFieldValidationException(String label) {
        this.label = label;
    }
}
