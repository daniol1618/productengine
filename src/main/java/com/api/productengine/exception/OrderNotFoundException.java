package com.api.productengine.exception;

public class OrderNotFoundException extends ResourceNotFoundException {
    public OrderNotFoundException(Long id) {
        super("Order not found with id: " + id);
    }
}
