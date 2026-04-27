package com.api.productengine.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Order() {}
    public Order(Product product) {
        this.product = product;
    }


    // Getters and Setters
    public Long getId() { return id; }

    public Product getProduct() { return product; }

    public void setProduct(Product product) { this.product = product; }
}