package com.api.productengine.dto;

public class OrderDTO {
    private Long productId;
    private Double saldo;

    public OrderDTO() { }

    public OrderDTO(Long productId, Double saldo) {
        this.productId = productId;
        this.saldo = saldo;
    }

    public Long getProductId() {
        return productId;
    }

    public Double getSaldo() {
        return saldo;
    }
}
