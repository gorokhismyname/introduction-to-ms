package com.example.song_service.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SongMetadataNotFoundException.class)
    public ResponseEntity<String> handleSongMetadataNotFoundException(SongMetadataNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The song metadata with the specified id does not exist. id: " + ex.getId());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid input: " + message);
    }

    @ExceptionHandler(InvalidCSVFormatException.class)
    public ResponseEntity<String> handleInvalidCSVFormatException(InvalidCSVFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID format: " + ex.getMessage() + ". Only positive integers allowed");
    }
}
