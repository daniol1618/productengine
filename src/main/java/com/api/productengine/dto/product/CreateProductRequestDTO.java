package com.api.productengine.dto.product;

import java.math.BigDecimal;

public record CreateProductRequestDTO(
        String name,
        String description,
        BigDecimal price,
        Integer stock
) {
}
