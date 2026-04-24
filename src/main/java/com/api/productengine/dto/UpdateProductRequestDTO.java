package com.api.productengine.dto;

import java.math.BigDecimal;

public record UpdateProductRequestDTO(
        String name,
        String description,
        BigDecimal price,
        Integer stock
) {
}
