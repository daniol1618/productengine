package com.api.productengine.dto.product;

import java.math.BigDecimal;

public record ProductStatsResponseDTO(
        BigDecimal totalStockValue,
        BigDecimal averagePrice
) {
}
