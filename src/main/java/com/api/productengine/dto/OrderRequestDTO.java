package com.api.productengine.dto;

import java.time.LocalDate;

import com.api.productengine.model.Product;

public class OrderRequestDTO {
    private Long productId;
    private Integer quantity;

    public Long getProductId(){
        return productId;
    }

    public Integer getQuantity(){
        return quantity;
    }
}
