package com.api.productengine.dto;

public class OrderRequestDTO {
    private Long productId;

    public OrderRequestDTO() {}

    public OrderRequestDTO(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
}
