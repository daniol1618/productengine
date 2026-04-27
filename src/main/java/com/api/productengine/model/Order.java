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


    @Column(nullable = false)
    private BigDecimal total;


    // Constructors
    public Order(Long id, BigDecimal total, Product product) {
        this.id = id;
        this.total = total;
        this.product = product;
    }

    public Order() {}


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}