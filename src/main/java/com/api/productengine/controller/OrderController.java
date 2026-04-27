package com.api.productengine.controller;

import com.api.productengine.dto.OrderDTO;
import com.api.productengine.mapper.OrderMapper;
import com.api.productengine.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderDTO create(@RequestBody OrderDTO dto) {
        return OrderMapper.toDTO(orderService.create(dto));
    }

    @GetMapping
    public List<OrderDTO> getAll() {
        return orderService.findAll()
                .stream()
                .map(OrderMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public OrderDTO getById(@PathVariable Long id) {
        return OrderMapper.toDTO(orderService.findById(id));
    }

    @PutMapping("/{id}")
    public OrderDTO update(@PathVariable Long id, @RequestBody OrderDTO dto) {
        return OrderMapper.toDTO(orderService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }
}