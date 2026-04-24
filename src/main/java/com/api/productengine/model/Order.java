package com.api.productengine.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product requestedProduct;

    @Column(name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "final_price")
    private BigDecimal finalPrice;

    public Order(Product requestedProduct, BigDecimal finalPrice) {
        this.requestedProduct = requestedProduct;
        this.finalPrice = finalPrice;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public Product getRequestedProduct() {
        return requestedProduct;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setRequestedProduct(Product requestedProduct) {
        this.requestedProduct = requestedProduct;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
