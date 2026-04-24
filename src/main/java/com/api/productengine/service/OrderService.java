package com.api.productengine.service;

import com.api.productengine.model.Order;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.service.ProductService;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    public Order create(OrderDTO orderDTO) {
        // throw exception if not found
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException("Invalid product.", HttpStatus.BAD_REQUEST));

        try {
            Order order = new Order(product, orderDTO.getPrice());
            order.isOrderValid();
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return repository.save(order);
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException("Order not found"));
    }

    public Order update(Long id, OrderDTO orderDTOUpdated) {
        Order existing = findById(id);
        // throw exception if not found
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException("Invalid product.", HttpStatus.BAD_REQUEST));

        existing.setPrice(orderDTOUpdated.getPrice());

        try {
            existing.setProduct(product);
            Order order = new Order(product, orderDTO.getPrice());
            order.isOrderValid();
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
