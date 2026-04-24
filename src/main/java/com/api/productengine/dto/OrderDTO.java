package com.api.productengine.dto;

import java.math.BigDecimal;

public class OrderDTO {
    private Long productId;
    private BigDecimal price;

    public long getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
