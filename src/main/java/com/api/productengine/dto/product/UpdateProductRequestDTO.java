package com.api.productengine.dto.product;

import java.math.BigDecimal;

public record UpdateProductRequestDTO(
        String name,
        String description,
        BigDecimal price,
        Integer stock
) {
}
