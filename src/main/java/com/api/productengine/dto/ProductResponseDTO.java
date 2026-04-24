package com.api.productengine.dto;

import com.api.productengine.model.Product;

import java.math.BigDecimal;

public record ProductResponseDTO(
    Long id,
    String name,
    String description,
    BigDecimal price,
    Integer stock
) {

    public static ProductResponseDTO fromProduct(Product product){
        return new ProductResponseDTO(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }
}
