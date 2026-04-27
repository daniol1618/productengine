package com.api.productengine.controller;

import com.api.productengine.dto.order.OrderCreateRequestDTO;
import com.api.productengine.dto.order.OrderResponseDTO;
import com.api.productengine.service.OrderService;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderCreateRequestDTO createRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.orderService.createOrder(createRequest));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAll(){
        return ResponseEntity.ok(this.orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.orderService.findById(id));
    }
}
