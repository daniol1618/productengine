package com.api.productengine.dto;

import java.math.BigDecimal;

public record OrderResponseDTO(
        Long id,
        String orderNumber,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal totalAmount
) {}