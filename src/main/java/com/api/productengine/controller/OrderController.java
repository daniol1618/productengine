package com.api.productengine.controller;

import com.api.productengine.dto.OrderRequestDTO;
import com.api.productengine.dto.OrderResponseDTO;
import com.api.productengine.service.OrderService;
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
    public OrderResponseDTO create(@RequestBody OrderRequestDTO request) {
        return service.create(request);
    }

    @GetMapping
    public List<OrderResponseDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public OrderResponseDTO update(@PathVariable Long id, @RequestBody OrderRequestDTO request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}