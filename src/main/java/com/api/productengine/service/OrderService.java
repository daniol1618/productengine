package com.api.productengine.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.productengine.dto.OrderDTO;
import com.api.productengine.exception.BusinessException;
import com.api.productengine.exception.OrderNotFoundException;
import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductService productService;

    public OrderService(OrderRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    @Transactional
    public Order create(OrderDTO orderDto) {
        Product product = productService.reserveOneUnit(orderDto.productId());

        if (product.getPrice().signum() != 1) {
            throw new BusinessException("Price must be higher than 0");
        } 

        LocalDateTime orderDateTime = LocalDateTime.now();
        BigDecimal orderPrice = product.getPrice();

        Order order = new Order(product, orderDateTime, orderPrice);
        return repository.save(order);
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
