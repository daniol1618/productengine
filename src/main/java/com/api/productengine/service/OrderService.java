package com.api.productengine.service;

import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("El producto no existe."));

        if (product.getStock() <= 0) {
            throw new RuntimeException("El producto no cuenta con existencias.");
        }

        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("La orden debe tener un saldo positivo.");
        }

        product.setStock(product.getStock());
        productRepository.save(product);

        Order order = new Order();
        order.setProduct(product);
        order.setTotal(product.getPrice());
        order.setDate(LocalDateTime.now());

        return orderRepository.save(order);
    }
}