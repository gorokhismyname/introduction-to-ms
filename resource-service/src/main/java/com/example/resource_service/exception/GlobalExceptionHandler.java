package com.example.resource_service.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

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

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid audio data: " + ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid value: " + ex.getMessage());
    }

    @ExceptionHandler(InvalidCSVFormatException.class)
    public ResponseEntity<String> handleInvalidCSVFormatException(InvalidCSVFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID format: " + ex.getMessage() + ". Only positive integers allowed");
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

}
