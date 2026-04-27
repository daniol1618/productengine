package com.api.productengine.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(
    String name,
    String description,
    BigDecimal price
){}
