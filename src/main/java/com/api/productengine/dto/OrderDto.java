package com.api.productengine.dto;

public class OrderDto {


    private Long product_id;

    private Double amount;

    public OrderDto() {
    }

    public OrderDto(Long product_id, Double amount) {
        this.product_id = product_id;
        this.amount = amount;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "product_id=" + product_id +
                ", amount=" + amount +
                '}';
    }
}
