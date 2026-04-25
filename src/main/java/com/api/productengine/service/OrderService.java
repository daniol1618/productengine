package com.api.productengine.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.api.productengine.dto.OrderRequestDTO;
import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    private final OrderRepository order_repository;
    private final ProductService productService;

    public OrderService(OrderRepository repository, ProductService productService) {
        this.order_repository = repository;
        this.productService = productService;
    }



   @Transactional   
    public Order create(OrderRequestDTO orderDTO){
        //producto debe existir para poder ser agregado a la orden.

        Product product = productService.findById(orderDTO.getProduct().getId());

        //La orden no puede tener 0 productos.
        if (orderDTO.getQty() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(orderDTO.getQty()));

        //La orden debe tener un saldo positivo.
        if(total.compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("El total debe ser mayor a 0");
        }

        //El producto debe contar con existencias (stock) parar poder ser generada la orden.
        if (product.getStock() <= 0) {
            throw new RuntimeException("El stock debe ser mayor a 0");
        }

        Order order = new Order(product,orderDTO.getQty(),total);
        return order_repository.save(order);
        }
}
