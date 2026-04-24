package com.api.productengine.service;

import com.api.productengine.dto.order.OrderCreateRequestDTO;
import com.api.productengine.dto.order.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(OrderCreateRequestDTO requestDTO);

    OrderResponseDTO findById(Long id);

    List<OrderResponseDTO> findAll();
}
