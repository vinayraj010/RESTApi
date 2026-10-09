package com.spring.RESTApi.exception;

import com.spring.RESTApi.response.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>>
    handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        ApiResponse<Map<String, String>> response =
                new ApiResponse<>(
                        false,
                        "Validation failed",
                        errors
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>>
    handleResourceNotFound(
            ResourceNotFoundException ex) {

        ApiResponse<Void> response =
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>>
    handleIllegalArgument(
            IllegalArgumentException ex) {

        ApiResponse<Void> response =
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>>
    handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        ApiResponse<Void> response =
                new ApiResponse<>(
                        false,
                        "Email already exists",
                        null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>>
    handleGenericException(Exception ex) {

        ApiResponse<Void> response =
                new ApiResponse<>(
                        false,
                        "Something went wrong. Please try again later.",
                        null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}