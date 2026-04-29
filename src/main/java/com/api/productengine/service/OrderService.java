package com.api.productengine.service;

import java.net.http.HttpTimeoutException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.repository.ProductRepository;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductRepository productoRepo;

    OrderService(OrderRepository repository, ProductRepository productoRepo) {
        this.repository = repository;
        this.productoRepo = productoRepo;
    }

    public Order create(Order order) {
        Product existingProduct = productoRepo.findById(order.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        if (existingProduct.getStock() <= 0) {
            throw new RuntimeException("No hay stock");
        }
        existingProduct.setStock(existingProduct.getStock() - 1);
        return repository.save(order);
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order update(Long id, Order updated) {

        Order existing = findById(id);

        existing.setName(updated.getName());
        existing.setProduct(updated.getProduct());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
