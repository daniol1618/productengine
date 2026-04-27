package com.api.productengine.service;

import com.api.productengine.dto.OrderDTO;
import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    //CRUD

    public Order create(OrderDTO dto) {

        //Que el producto exista
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        //Stock
        if (product.getStock() == null || product.getStock() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "product out of stock");
        }

        //Total de una orden uno a uno con el producto
        BigDecimal total = product.getPrice();

        //No puede ser una orden gratis
        if (total == null || total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "We caanot give a free Order");
        }

        //Podria también reducir el stock
        product.setStock(product.getStock() - 1);
        productRepository.save(product);

        Order order = new Order();
        order.setProduct(product);
        order.setTotal(total);
        return orderRepository.save(order);
    }

    public List<Order> findAll() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No orders found");
        }
        return orders;
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    public Order update(Long id, OrderDTO dto) {

        Order existing = findById(id);

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (product.getStock() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product out of stock, sorry");
        }

        BigDecimal total = product.getPrice();

        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid total");
        }

        product.setStock(product.getStock() - 1);
        productRepository.save(product);

        existing.setProduct(product);
        existing.setTotal(total);

        return orderRepository.save(existing);
    }

    public void delete(Long id) {
        Order existing = findById(id);
        orderRepository.delete(existing);
    }
}
