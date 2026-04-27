package com.api.productengine.service;

import com.api.productengine.model.Product;
import com.api.productengine.model.Order;
import com.api.productengine.repository.ProductRepository;
import com.api.productengine.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    private boolean canCreateOrder(Product product) {
        Product existingProduct = productRepository.findById(product.getId()).
                orElseThrow(() -> new RuntimeException("Product not found"));
        return existingProduct.getPrice().compareTo(BigDecimal.ZERO)> 0 
                && existingProduct.getStock() > 0;
    }


    public Order create(String name, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (canCreateOrder(product)) {
            return orderRepository.save(new Order(name, product));
        }
        else {
            throw new RuntimeException("Cannot create order for product: " + product.getName());
        }
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public Order update(Long id, String name, Long productId) {
        Order existing = findById(id);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        existing.setName(name);
        existing.setProduct(product);
        return orderRepository.save(existing);
    }

}
