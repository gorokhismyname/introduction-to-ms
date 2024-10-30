package com.example.resource_service.exception;

import lombok.Getter;

@Getter
public class AudioResourceNotFoundException extends RuntimeException {
    private int id;
    public AudioResourceNotFoundException(Integer id) {
        this.id = id;
    }
}
