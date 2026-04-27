package com.api.productengine.service;

import org.springframework.stereotype.Service;

import com.api.productengine.exception.BusinessException;
import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.repository.ProductRepository;

import jakarta.transaction.Transactional;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // Inyección por constructor (Recomendado)
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(Long productId) {
        // 1. Validar si el producto existe
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new BusinessException("El producto no existe."));

        // 2. Validar stock
        if (product.getStock() <= 0) {
            throw new BusinessException("El producto no tiene existencias.");
        }

        // 3. Validar saldo (precio) > 0
        if (product.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new BusinessException("La orden debe tener un saldo mayor a $0.");
        }

        // 4. Lógica de negocio: Descontar stock
        product.setStock(product.getStock() - 1);
        productRepository.save(product);

        // 5. Crear y persistir la orden
        Order order = new Order();
        order.setProduct(product);
        order.setTotalAmount(product.getPrice());

        return orderRepository.save(order);
    }

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public java.util.List<Order> findAll() {
        return orderRepository.findAll();
    }
}
