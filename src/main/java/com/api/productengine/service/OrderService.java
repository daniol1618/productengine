package com.api.productengine.service;

import com.api.productengine.controller.InvalidOrderException;
import com.api.productengine.controller.ResourceNotFoundException;
import com.api.productengine.dto.OrderDTO;
import com.api.productengine.dto.OrderMapper;
import com.api.productengine.dto.OrderResponseDTO;
import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.productRepository = productRepository;
    }


    public OrderResponseDTO findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toOrderResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("No order with id: " + id));
    }


    public OrderResponseDTO saveOrder(OrderDTO orderDTO){
        Product product = this.productRepository.findById(orderDTO.productId()).
                orElseThrow(() -> new ResourceNotFoundException("No product with id: " + orderDTO.productId()));
        if(!Objects.equals(product.getPrice(), orderDTO.price())){
            throw new InvalidOrderException("The order price has to be equal to the product price");
        }
        Order savedOrder = orderRepository.save(this.orderMapper.toOrder(orderDTO,product));
        return this.orderMapper.toOrderResponseDTO(savedOrder);
    }


    public List<OrderResponseDTO> getAll(){
        return this.orderRepository.findAll().stream().map(this.orderMapper::toOrderResponseDTO).collect(Collectors.toList());
    }
}
