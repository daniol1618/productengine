package com.api.productengine.controller;


import com.api.productengine.model.Order;
import com.api.productengine.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    /**
     * Crear una nueva orden
     */
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order request) {
        Order response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtener todas las órdenes
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        List<Order> orders = service.findAll();
        return ResponseEntity.ok(orders);
    }

    /**
     * Obtener una orden por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        Order order = service.findById(id);
        return ResponseEntity.ok(order);
    }

    /**
     * Obtener órdenes por producto ID
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Order>> getByProductId(@PathVariable Long productId) {
        List<Order> orders = service.findByProductId(productId);
        return ResponseEntity.ok(orders);
    }
}
