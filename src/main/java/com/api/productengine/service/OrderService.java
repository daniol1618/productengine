package com.api.productengine.service;

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

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(Long productId) {

        //busco el producto ordenado
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("El producto no existe"));

        //tiene q tener precio
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("No se puede generar una orden con saldo $0 o menor");
        }

        //tiene q tener stock
        if (product.getStock() <= 0) {
            throw new RuntimeException("El producto no tiene stock disponible");
        }

        //descontar una unidad
        product.setStock(product.getStock() - 1);

        productRepository.save(product);

        // Creamos la orden
        Order newOrder = new Order();
        newOrder.setProduct(product);
        newOrder.setTotalAmount(product.getPrice());

        return orderRepository.save(newOrder);
    }


    public List<Order> findAllOrders(){
        return orderRepository.findAll();
    }
}
