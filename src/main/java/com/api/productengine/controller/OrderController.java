package com.api.productengine.controller;

//import com.api.productengine.dto.OrderRequestDTO;
import com.api.productengine.model.Order;
import com.api.productengine.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

}