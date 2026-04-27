package com.api.productengine.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    
    @Transactional
    public Order createOrder(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if(orderRepository.existsByProductId(productId)) {
            throw new IllegalStateException("The product is already associated with an order");
        }
        
        if(product.getStock() <= 0) {
            throw new IllegalStateException("Product is out of stock");
        }

        if(product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }
        
        product.setStock(product.getStock() - 1);
        productRepository.save(product);

        Order order = new Order();
        order.setProduct(product);
        order.setTotalPrice(product.getPrice());

        return orderRepository.save(order);
    }
}