package com.api.productengine.service;

import com.api.productengine.dto.OrderRequestDTO;
import com.api.productengine.dto.OrderResponseDTO;
import com.api.productengine.exception.InsufficientStockException;
import com.api.productengine.exception.InvalidOrderException;
import com.api.productengine.exception.OrderNotFoundException;
import com.api.productengine.exception.ProductNotFoundException;
import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.repository.ProductRepository;
import org.springframework.stereotype.Service;

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

    /**
     * Crear una nueva orden con validaciones de negocio.
     * Cada orden contiene un único producto con cantidad 1.
     */
    public OrderResponseDTO create(OrderRequestDTO request) {
        // Validar que se envió un productId
        if (request.getProductId() == null) {
            throw new InvalidOrderException("El ID del producto es requerido");
        }

        // Validar que el producto existe
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(
                        "El producto con ID " + request.getProductId() + " no existe"));

        // Validar que el precio del producto es > 0 (saldo de la orden > $0)
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidOrderException("El precio del producto debe ser mayor a $0");
        }

        // Validar que existe stock disponible (al menos 1 unidad)
        if (product.getStock() == null || product.getStock() < 1) {
            throw new InsufficientStockException(
                    "Stock insuficiente para el producto '" + product.getName() +
                    "'. Disponible: " + product.getStock());
        }

        // Crear la orden (1 unidad, totalPrice = precio del producto)
        Order order = new Order(product, product.getPrice());
        Order savedOrder = orderRepository.save(order);

        // Descontar 1 unidad del stock
        product.setStock(product.getStock() - 1);
        productRepository.save(product);

        // Retornar DTO
        return new OrderResponseDTO(
                savedOrder.getId(),
                product.getId(),
                product.getName(),
                savedOrder.getTotalPrice(),
                savedOrder.getStatus().toString(),
                savedOrder.getCreatedAt()
        );
    }

    /**
     * Obtener todas las órdenes
     */
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    /**
     * Obtener una orden por ID
     */
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Orden no encontrada con ID: " + id));
    }

    /**
     * Obtener órdenes por producto ID
     */
    public List<Order> findByProductId(Long productId) {
        return orderRepository.findByProductId(productId);
    }
}
