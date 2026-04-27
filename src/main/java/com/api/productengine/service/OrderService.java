package com.api.productengine.service;

import com.api.productengine.dto.OrderDto;
import com.api.productengine.exception.InvalidAmountException;
import com.api.productengine.exception.NotFoundException;
import com.api.productengine.exception.OutOfStockException;
import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService){
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public Order createOrder(OrderDto create){
        Order order = new Order();
        if(create.getAmount() < 0) throw new InvalidAmountException();
        order.setAmount(create.getAmount());

        Product product = productService.findById(create.getProduct_id());

        if(product.getStock().equals(0)) throw new OutOfStockException();
        order.setProduct(product);
        product.setStock(product.getStock()-1);

        productService.update(product.getId(),product);
        return orderRepository.save(order);
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public Order findById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public Order update(Long id,OrderDto update){
        Order order = this.findById(id);

        if(update.getAmount() < 0) throw new InvalidAmountException();
        order.setAmount(update.getAmount());

        if(!order.getProduct().getId().equals(update.getProduct_id())) {
            Product product = productService.findById(update.getProduct_id());

            if(product.getStock().equals(0)) throw new OutOfStockException();
            Product oldProduct = order.getProduct();
            oldProduct.setStock(oldProduct.getStock() + 1);
            productService.update(oldProduct.getId(), oldProduct);

            order.setProduct(product);
            product.setStock(product.getStock()-1);

            productService.update(product.getId(),product);
        }

        return orderRepository.save(order);
    }

    public void delete(Long id){
        orderRepository.deleteById(id);
        return;
    }

}
