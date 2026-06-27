package com.sky2dev.day26.exception;

import com.sky2dev.day26.dto.ApiResponse;
import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                "RESOURCE_NOT_FOUND",
                ex.getMessage(),
                List.of("Requested resource does not exist"),
                Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure("RESOURCE_NOT_FOUND", ex.getMessage(), error));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleValidation(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        ErrorResponse error = new ErrorResponse(
                "VALIDATION_FAILED",
                "Input validation failed",
                details,
                Instant.now());

        return ResponseEntity.badRequest()
                .body(ApiResponse.failure("VALIDATION_FAILED", "Validation error", error));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleUnexpected(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "INTERNAL_ERROR",
                "Unexpected server error",
                List.of(ex.getMessage()),
                Instant.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure("INTERNAL_ERROR", "Unexpected server error", error));
    }
}
