package com.api.productengine.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.productengine.dto.OrderRequestDTO;
import com.api.productengine.dto.OrderResponseDTO;
import com.api.productengine.model.Order;
import com.api.productengine.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponseDTO create(@RequestBody OrderRequestDTO dto){
        return orderService.create(dto);
    }
}
