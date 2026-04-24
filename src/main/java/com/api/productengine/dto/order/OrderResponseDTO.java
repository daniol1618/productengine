package com.api.productengine.dto.order;

import com.api.productengine.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponseDTO(
        Long id,
        // campos arbitrarios detalle del product
        Long productId,
        String productName,
        LocalDateTime orderDate,
        BigDecimal finalPrice
) {
    public static OrderResponseDTO fromOrder(Order order) {
        return new OrderResponseDTO(order.getId(),
                order.getRequestedProduct().getId(),
                order.getRequestedProduct().getName(),
                order.getOrderDate(),
                order.getFinalPrice());
    }
}
