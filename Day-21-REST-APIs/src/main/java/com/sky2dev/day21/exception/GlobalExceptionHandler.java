package com.sky2dev.day21.exception;

import com.sky2dev.day21.dto.ApiResponse;
import com.sky2dev.day21.dto.ErrorDetail;
import com.sky2dev.day21.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleValidationException(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure("REQUEST INVALID", ex.getMessage(), new ErrorResponse("VALIDATION_EXCEPTION", List.of())));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure("RESOURCE NOT FOUND", ex.getMessage(), new ErrorResponse("ENTITY_NOT_FOUND", List.of())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErrorDetail> details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorDetail(error.getField(), error.getDefaultMessage()))
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure("REQUEST INVALID", "Validation failed", new ErrorResponse("METHOD_ARGUMENT_NOT_VALID", details)));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure("REQUEST FAILED", ex.getMessage(), new ErrorResponse("ILLEGAL_ARGUMENT", List.of())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleAny(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure("REQUEST ERROR", ex.getMessage(), new ErrorResponse("UNEXPECTED_ERROR", List.of())));
    }
}
