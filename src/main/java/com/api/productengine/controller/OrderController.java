package com.api.productengine.controller;

import com.api.productengine.dto.OrderDto;
import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.service.OrderService;
import com.api.productengine.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public Order create(@RequestBody OrderDto order){ return  this.orderService.createOrder(order);}

    @GetMapping
    public List<Order> getAllOrders(){return this.orderService.findAll();}

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {

        return orderService.findById(id);
    }

    @PutMapping("/{id}")
    public Order update(@PathVariable Long id, @RequestBody OrderDto order) {

        return orderService.update(id, order);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        orderService.delete(id);
    }
}
