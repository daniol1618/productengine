package com.api.productengine.service;


import com.api.productengine.dto.OrderDTO;
import com.api.productengine.exception.BadRequestException;
import com.api.productengine.exception.OrderNotFoundException;
import com.api.productengine.exception.ResourceNotFoundException;
import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductRepository productRepository;


    public OrderService(OrderRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order create(OrderDTO orderDTO) {
        Product product = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(() ->  new OrderNotFoundException(orderDTO.getProductId()));

        if (product.getStock() <= 0) {
            throw new BadRequestException("Producto sin stock.");
        }

        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("El precio debe ser mayor a 0.");
        }

        product.setStock(product.getStock() - 1);
        productRepository.save(product);

        Order newOrder = new Order(product);
        return repository.save(newOrder);
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
