package com.api.productengine.dto;

import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderMapper {

    public OrderResponseDTO toOrderResponseDTO(Order order){
        return new OrderResponseDTO(new ProductResponseDTO(order.getProduct().getName(),
                                                        order.getProduct().getDescription(),
                                                        order.getProduct().getPrice()),
                order.getDateAndTimeOfOrder(),
                order.getPrice());
    }

    public Order toOrder(OrderDTO orderDTO, Product product) {

        if(orderDTO == null){
            throw new NullPointerException("The order DTO should not be null");
        }

        Order order = new Order();
        order.setPrice(orderDTO.price());
        order.setDateAndTimeOfOrder(LocalDateTime.now());
        order.setProduct(product);

        return order;
    }
}
