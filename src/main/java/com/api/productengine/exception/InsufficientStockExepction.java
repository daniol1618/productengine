package com.api.productengine.exception;

public class InsufficientStockExepction extends RuntimeException {
    public InsufficientStockExepction(Long productId) {
        super(String.format("Product %d is not available at the moment", productId));
    }
}
