package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el producto (un producto por orden)
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    private LocalDateTime orderDate;

    // Constructors
    public Order() {
        this.orderDate = LocalDateTime.now();
    }

    public Order(Product product) {
        this.product = product;
        // Al ser siempre 1 unidad, el total_price es el precio del producto
        this.totalPrice = product.getPrice();
        this.orderDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }

    public Product getProduct() { return product; }

    public void setProduct(Product product) {
        this.product = product;
        if (product != null) {
            this.totalPrice = product.getPrice();
        }
    }

    public BigDecimal getTotalPrice() { return totalPrice; }

    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public LocalDateTime getOrderDate() { return orderDate; }
}