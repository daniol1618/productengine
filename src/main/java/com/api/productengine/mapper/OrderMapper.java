package com.api.productengine.mapper;

import com.api.productengine.dto.OrderDTO;
import com.api.productengine.dto.ProductDTO;
import com.api.productengine.model.Order;
import com.api.productengine.model.Product;

public class OrderMapper {

    public static OrderDTO toDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getProduct().getId(),
                order.getTotal()
        );
    }

    public static Order toEntity(OrderDTO dto) {
        Order order = new Order();
        order.setTotal(dto.getTotal());
        return order;
    }
}
