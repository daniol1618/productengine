package com.api.productengine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.api.productengine.dto.OrderDTO;
import com.api.productengine.model.Order;
import com.api.productengine.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order create(@RequestBody OrderDTO orderDto) {
        return service.create(orderDto);
    }

    @GetMapping
    public List<Order> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
