package com.api.productengine.service;

import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;

import com.api.productengine.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order create(Order order) {
        if (order.getProductId() == null) {
            throw(new IllegalArgumentException("Product Id is required"));
        } else if (order.getQuantity()<=0) {
            throw(new IllegalArgumentException("Positive Quantity is required"));
        }else {

            Product product = productRepository.findById(order.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < order.getQuantity()) {
                throw new RuntimeException("Stock is not enough for " + product.getName());
            }

            product.setStock(product.getStock() - order.getQuantity());
            productRepository.save(product);
        }
        return orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order update(Long id, Order updated) {
        Order existing = findById(id);

        existing.setProductId(updated.getProductId());

        return orderRepository.save(existing);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
