package com.api.productengine.exception;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(Long id) {
        super("Product not found with ID: " + id);
    }
}
