package com.api.productengine.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponseDTO (
    ProductResponseDTO product,
    LocalDateTime dateAndTimeOfOrder,

    BigDecimal price
){}
