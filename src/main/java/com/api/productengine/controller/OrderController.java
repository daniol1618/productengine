package com.api.productengine.controller;

import com.api.productengine.dto.OrderDTO;
import com.api.productengine.dto.OrderResponseDTO;
import com.api.productengine.model.Order;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;


    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/api/orders/{id}")
    public OrderResponseDTO getById(@PathVariable Long id){
        return this.orderService.findById(id);
    }

    @GetMapping("/api/orders")
    public List<OrderResponseDTO> getAll(){
        return this.orderService.getAll();
    }

    @PostMapping("/api/orders")
    public OrderResponseDTO create(@RequestBody OrderDTO order){
        return this.orderService.saveOrder(order);
    }
}
