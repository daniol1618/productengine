package com.api.productengine.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.api.productengine.dto.OrderRequestDTO;
import com.api.productengine.dto.OrderResponseDTO;
import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.repository.ProductRepository;
import com.api.productengine.errors.*;;

@Service
public class OrderService {
    private OrderRepository repository;
    private ProductRepository productRepository;
    
    public OrderService(OrderRepository repository, ProductRepository productRepository){
        this.repository = repository;
        this.productRepository = productRepository;
    }

    public OrderResponseDTO create(OrderRequestDTO order){
        var productId = order.getProductId();
        var quantity = order.getQuantity();
        var product = productRepository.findById(productId).
            orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        var stock = product.getStock();
        if(product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("El precio de la orden debe ser mayor a 0");
        }
        if(stock < quantity){
            throw new NoStockException("The current stock is: " + stock.toString() + ". And you requested for: " + quantity);
        }
        var newOrder = new Order(product, quantity);
        product.setStock(stock - quantity);
        productRepository.save(product);
        repository.save(newOrder);
        var orderResponse = new OrderResponseDTO(quantity, newOrder.getCreationDate() , product);
        return orderResponse; 
    }
}
