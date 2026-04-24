package com.api.productengine.model;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.api.productengine.errors.ErrorResponse;
import com.api.productengine.errors.InvalidPriceException;
import com.api.productengine.errors.NoStockException;
import com.api.productengine.errors.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidPriceException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPrice(InvalidPriceException ex){
        ErrorResponse error = new ErrorResponse(400, ex.getMessage());
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(NoStockException.class)
    public ResponseEntity<ErrorResponse> handleNoStock(NoStockException ex){
        ErrorResponse error = new ErrorResponse(400, ex.getMessage());
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundElement(ResourceNotFoundException ex){
        ErrorResponse error = new ErrorResponse(404, ex.getMessage());
        return ResponseEntity.status(404).body(error);
    }

}
