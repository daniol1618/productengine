package com.api.productengine.controller;

import com.api.productengine.dto.order.OrderCreateRequestDTO;
import com.api.productengine.dto.order.OrderResponseDTO;
import com.api.productengine.service.OrderService;
import com.api.productengine.service.impl.OrderServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponseDTO create(@RequestBody OrderCreateRequestDTO createRequest) {
        return this.orderService.createOrder(createRequest);
    }

    @GetMapping
    public List<OrderResponseDTO> getAll(){
        return this.orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderResponseDTO findById(@PathVariable("id") Long id){
        return this.orderService.findById(id);
    }
}
