package com.api.productengine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.productengine.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Boolean existsByProductId(Long productId);
}
