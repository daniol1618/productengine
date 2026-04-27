package com.api.productengine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return error(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        return error(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> error(String msg, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(msg, status.value()), status);
    }
}
