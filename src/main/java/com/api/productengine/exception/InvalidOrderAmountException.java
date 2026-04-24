package com.api.productengine.exception;

public class InvalidOrderAmountException extends RuntimeException {
    public InvalidOrderAmountException(String message) {
        super(message);
    }
}