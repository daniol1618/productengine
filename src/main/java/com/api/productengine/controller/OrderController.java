package com.api.productengine.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.productengine.model.Order;
import com.api.productengine.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{productId}")
    public ResponseEntity<Order> generateOrder(@PathVariable Long productId) {
    
    Order newOrder = orderService.createOrder(productId);
    return new ResponseEntity<>(newOrder, HttpStatus.CREATED);   
    }
}
