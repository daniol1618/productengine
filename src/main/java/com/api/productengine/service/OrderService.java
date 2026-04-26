package com.api.productengine.service;

import com.api.productengine.dto.OrderDTO;
import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }
    
    @Transactional
    public Order create(OrderDTO orderDTO){
        Product product = productRepository.findById(orderDTO.getProductId())
        .orElseThrow(() -> new RuntimeException("El producto solicitado no existe"));

        if(orderRepository.existsByProductId(product.getId())){
            throw new IllegalStateException("Este producto ya existe a una orden existente");
        }

        if(product.getStock() < 1){
            throw new IllegalStateException("El producto no tiene stock");
        }

        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("La orden no puede procesarse porque el precio es de $0");
        }

        product.setStock(product.getStock()-1);
        productRepository.save(product);

        Order order = new Order();
        order.setProduct(product);
        order.setTotalAmount(product.getPrice());

        return orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void delete(Long id) {
        Order order = orderRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("El orden no existe"));

        Product product = order.getProduct();
        product.setStock(product.getStock()+1);
        productRepository.save(product);
        
        orderRepository.deleteById(id);
    }
}
