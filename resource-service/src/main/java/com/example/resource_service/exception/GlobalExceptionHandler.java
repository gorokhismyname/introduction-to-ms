package com.example.resource_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<String> handleMaxSizeException(FileProcessingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed or request body is invalid MP3");
    }

    @ExceptionHandler(AudioResourceNotFoundException.class)
    public ResponseEntity<String> handleAudioResourceNotFoundException(AudioResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The resource with the specified id does not exist. id: " + ex.getId());
    }

}
