package com.api.productengine.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<MyErrorResponse> handleNotFound(ResourceNotFoundException exception){
        MyErrorResponse error = new MyErrorResponse(HttpStatus.NOT_FOUND.value(),exception.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidOrderException.class)
    public ResponseEntity<MyErrorResponse> handleNotFound(InvalidOrderException exception){
        MyErrorResponse error = new MyErrorResponse(HttpStatus.BAD_REQUEST.value(),exception.getMessage());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }


}
