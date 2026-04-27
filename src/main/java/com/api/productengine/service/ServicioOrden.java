package com.api.productengine.service;

import com.api.productengine.model.Orden;
import com.api.productengine.model.Product;
import com.api.productengine.repository.RepositorioOrden;

import jakarta.transaction.Transactional;

import com.api.productengine.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServicioOrden {

    private final RepositorioOrden orderRepository;
    private final ProductRepository productRepository;

    public ServicioOrden(RepositorioOrden orderRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Orden createOrder(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() <= 0) {
            throw new RuntimeException("Product has no stock");
        }

        if (product.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El total de la orden debe ser positivo");
        }

        Orden order = new Orden();
        order.setProduct(product);
        order.setTotal(product.getPrecio());

        product.setStock(product.getStock() - 1);
        productRepository.save(product);

        return orderRepository.save(order);
    }

    public List<Orden> findAll() {
        return orderRepository.findAll();
    }

}