package com.api.productengine.service;


import com.api.productengine.dto.OrderDTO;
import com.api.productengine.exceptions.BadRequestException;
import com.api.productengine.exceptions.ResourceNotFoundException;
import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.repository.ProductRepository;
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

    public Order create(OrderDTO orderDTO) {
        Product product = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("El producto no existe."));

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
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }


    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("La orden no existe.");
        }
        repository.deleteById(id);
    }
}
