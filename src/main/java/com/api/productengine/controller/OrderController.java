package com.api.productengine.controller;

import com.api.productengine.model.Order;
import com.api.productengine.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Order> create(@PathVariable Long productId) {
        try {
            Order createdOrder = orderService.createOrder(productId);
            return ResponseEntity.ok(createdOrder);
        } catch (RuntimeException e) {
            // Aquí capturamos las excepciones de las restricciones
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<Order> getAll() {
        return orderService.findAllOrders();
    }
}
