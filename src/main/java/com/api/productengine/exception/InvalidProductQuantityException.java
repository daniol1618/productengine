package com.api.productengine.exception;

public class InvalidProductQuantityException extends RuntimeException {
    public InvalidProductQuantityException(String message) {
        super(message);
    }
}