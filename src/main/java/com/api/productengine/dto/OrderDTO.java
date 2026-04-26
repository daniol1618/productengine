package com.api.productengine.dto;

public class OrderDTO {
    private Long productId;

    public OrderDTO() {}

    public OrderDTO(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
