package com.api.productengine.controller;

import com.api.productengine.dto.OrderDTO;
import com.api.productengine.dto.ProductDTO;
import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.service.OrderService;
import com.api.productengine.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderDTO orderDTO) {
        Order newOrder = service.create(orderDTO);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll() {
        List<Order> orders = service.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        Order order = service.findById(id);
        return ResponseEntity.ok(order);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}