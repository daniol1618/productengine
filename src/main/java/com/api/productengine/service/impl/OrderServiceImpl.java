package com.api.productengine.service.impl;

import com.api.productengine.dto.order.OrderCreateRequestDTO;
import com.api.productengine.dto.order.OrderResponseDTO;
import com.api.productengine.exception.InsufficientStockExepction;
import com.api.productengine.exception.ResourceNotFoundException;
import com.api.productengine.model.Order;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.repository.ProductRepository;
import com.api.productengine.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    // transaccion de base de datos por si falla el primer save
    @Transactional
    public OrderResponseDTO createOrder(OrderCreateRequestDTO requestDTO){
        if(requestDTO == null) throw new IllegalArgumentException("Order must not be null");
        if(requestDTO.productId() == null) throw new IllegalArgumentException("Requestd product must not be null");

        var requestedProduct = productRepository.findById(requestDTO.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product of id: " + requestDTO.productId() + " was not found" ));

        if (requestedProduct.getStock() < 1)
            throw new InsufficientStockExepction(requestedProduct.getId());

        if(requestedProduct.getPrice().compareTo(BigDecimal.ZERO) <= 0 )
            throw new IllegalArgumentException("Order cannot have final price less than 0");

        requestedProduct.setStock(requestedProduct.getStock() - 1);
        Order newOrder = new Order(requestedProduct, requestedProduct.getPrice());

        // con transactional se supone no hace falta hacer save del product
        return OrderResponseDTO.fromOrder(orderRepository.save(newOrder));
    }

    @Override
    public OrderResponseDTO findById(Long id){
        return orderRepository.findById(id)
                .map(OrderResponseDTO::fromOrder)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderResponseDTO> findAll(){
        return orderRepository.findAll()
                .stream()
                .map(OrderResponseDTO::fromOrder)
                .toList();
    }
}
