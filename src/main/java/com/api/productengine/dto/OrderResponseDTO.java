package com.api.productengine.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.api.productengine.model.Product;

public class OrderResponseDTO {
    private Integer quantity;

    private LocalDate creationDate;

    private Product product;

    public OrderResponseDTO(Integer quantity, LocalDate creationDate, Product product){
        this.quantity = quantity;
        this.creationDate = creationDate;
        this.product = product;
    }   

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Product getProduct() {
        return product;
    }
}
