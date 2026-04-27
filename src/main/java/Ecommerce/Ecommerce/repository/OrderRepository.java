package Ecommerce.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Ecommerce.Ecommerce.Model.Order;

public interface OrderRepository extends JpaRepository <Order, Long> {
    
}
