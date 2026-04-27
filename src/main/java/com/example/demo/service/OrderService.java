package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository; // Para actualizar el stock

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional // Importante: Si falla el descuento de stock, no se guarda la orden
    public Order createOrder(Long productId) {
        // 1. Validar si el producto existe (usamos el repo de productos)
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("El producto no existe."));

        // 2. Validar que el saldo (precio) sea mayor a 0
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("La orden debe tener un saldo mayor a $0.");
        }

        // 3. Validar que el producto tenga existencias (stock)
        if (product.getStock() < 1) {
            throw new RuntimeException("El producto no cuenta con existencias suficientes.");
        }

        // 4. Descontar el stock del producto
        product.setStock(product.getStock() - 1);
        productRepository.save(product);

        // 5. Crear y guardar la orden (cantidad siempre es 1 según tu regla)
        Order newOrder = new Order(product);
        return orderRepository.save(newOrder);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada."));
    }
}