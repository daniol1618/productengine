package com.api.productengine.dto;

public record OrderRequestDTO(
        Long productId,
        Integer quantity
) {}