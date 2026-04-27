package com.api.productengine.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private LocalDateTime orderDateTime;

    @Column
    private BigDecimal price;

    // Constructors
    public Order() {}

    public Order(Product product, LocalDateTime orderDateTime, BigDecimal price) {
        this.product = product;
        this.orderDateTime = orderDateTime;
        this.price = price;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
