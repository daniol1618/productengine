package com.api.productengine.service;

import com.api.productengine.dto.OrderDTO;
import com.api.productengine.exception.ResourceNotFoundException;
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

    public Order create(OrderDTO orderDTO) {
        Product product = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (product.getStock() < 1)
            throw new IllegalArgumentException("El producto no cuenta con el stock suficiente");
        if(orderDTO.getSaldo() <= 0.0)
            throw new IllegalArgumentException("El saldo debe ser positivo");
        Order order = new Order(product, orderDTO.getSaldo());
        return orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
