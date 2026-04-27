package com.api.productengine.service;

import com.api.productengine.dto.OrderDTO;
import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public Order create(OrderDTO dto) {

        Product product = productService.findById(dto.getProductId());

        if (product.getStock() < dto.getQuantity()) {
            throw new RuntimeException("stock insuficiente para el producto: " + product.getName());
        }

        if (dto.getQuantity() <= 0) {
            throw new RuntimeException("la cantidad debe ser mayor a 0");
        }

        BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("el total de la orden debe ser positivo");
        }

        product.setStock(product.getStock() - dto.getQuantity());

        Order order = new Order(product, dto.getQuantity(), total, LocalDateTime.now());
        return orderRepository.save(order);
    }
}