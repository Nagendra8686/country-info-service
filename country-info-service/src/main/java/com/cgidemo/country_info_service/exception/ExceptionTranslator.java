package com.cgidemo.country_info_service.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;


@ControllerAdvice
public class ExceptionTranslator {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid country code format");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleCountryNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientError(HttpClientErrorException ex) {
        if (ex.getMessage() != null && ex.getMessage().contains("404")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Country code not found");
        }
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Upstream API failure");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleUpstream(RuntimeException ex) {
        if (ex.getMessage() != null && ex.getMessage().contains("Upstream API error")) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Upstream API failure");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }

}
