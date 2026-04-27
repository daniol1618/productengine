package com.api.productengine.controller;

import com.api.productengine.dto.OrderRequest;
import com.api.productengine.dto.ProductDTO;
import com.api.productengine.model.Order;

import com.api.productengine.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order create(@RequestBody OrderRequest order) {
        return service.create(order.getName(), order.getProductId());
    }

    @GetMapping
    public List<Order> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Order update(@PathVariable Long id, @RequestBody OrderRequest order) {
        return service.update(id, order.getName(), order.getProductId());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}