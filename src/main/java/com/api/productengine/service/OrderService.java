package com.api.productengine.service;

import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

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

        if (product.getStock() == null || product.getStock() <= 0) {
            throw new RuntimeException("No hay stock disponible.");
        }

        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El saldo de la orden debe ser positivo.");
        }

        product.setStock(product.getStock() - 1);
        productRepository.save(product);

        Order order = new Order(product, product.getPrice());
        return orderRepository.save(order);
    }
}