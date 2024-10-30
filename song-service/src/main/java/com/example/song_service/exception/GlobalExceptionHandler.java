package com.example.song_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MetadataFieldValidationException.class)
    public ResponseEntity<String> handleMetadataFieldValidationException(MetadataFieldValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Metadata validation failed. Field missing: " + ex.getLabel());
    }

    @ExceptionHandler(SongMetadataNotFoundException.class)
    public ResponseEntity<String> handleSongMetadataNotFoundException(SongMetadataNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The song metadata with the specified id does not exist. id: " + ex.getId());
    }

}
