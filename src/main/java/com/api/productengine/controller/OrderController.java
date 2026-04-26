package com.api.productengine.controller;

import com.api.productengine.dto.OrderDTO;
import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.service.OrderService;
import org.springframework.http.HttpStatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tb_orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody OrderDTO request) {
        return orderService.create(request);
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @GetMapping
    public List<Order> getAll() {
        System.out.println("exposed dta");
        return orderService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }

}