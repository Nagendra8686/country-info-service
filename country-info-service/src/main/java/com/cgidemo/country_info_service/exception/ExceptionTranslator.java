package com.cgidemo.country_info_service.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;


@ControllerAdvice
public class ExceptionTranslator {
    /**
     * Handles exceptions caused by constraint violations, such as invalid input formats.
     *
     * @param ex The `ConstraintViolationException` thrown when validation fails.
     * @return A `ResponseEntity` with HTTP status 400 (Bad Request) and a message indicating invalid input format.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        ErrorResponse error = new ErrorResponse("400", "Invalid country code format");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(error);
    }

    /**
     * Handles exceptions when a requested resource (e.g., country) is not found.
     *
     * @param ex The `ResourceNotFoundException` thrown when the resource is not found.
     * @return A `ResponseEntity` with HTTP status 404 (Not Found) and the exception message.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCountryNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("404", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(error);
    }

    /**
     * Handles HTTP client errors, such as 404 errors from upstream APIs.
     *
     * @param ex The `HttpClientErrorException` thrown during HTTP communication.
     * @return A `ResponseEntity` with HTTP status 404 (Not Found) if the error message contains "404",
     * otherwise HTTP status 502 (Bad Gateway) with a message indicating upstream API failure.
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpClientError(HttpClientErrorException ex) {
        if (ex.getMessage() != null && ex.getMessage().contains("404")) {
            ErrorResponse error = new ErrorResponse("404", "Country code not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(error);
        }
        ErrorResponse error = new ErrorResponse("502", "Upstream API failure");
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).contentType(MediaType.APPLICATION_JSON).body(error);
    }

    /**
     * Handles runtime exceptions, including errors from upstream APIs or internal server issues.
     *
     * @param ex The `RuntimeException` thrown during application execution.
     * @return A `ResponseEntity` with HTTP status 502 (Bad Gateway) if the error message contains "Upstream API error",
     * otherwise HTTP status 500 (Internal Server Error) with a generic error message.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleUpstream(RuntimeException ex) {
        if (ex.getMessage() != null && ex.getMessage().contains("Upstream API error")) {
            ErrorResponse error = new ErrorResponse("502", "Upstream API failure");
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).contentType(MediaType.APPLICATION_JSON).body(error);
        }
        ErrorResponse error = new ErrorResponse("500", "Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(error);
    }
}