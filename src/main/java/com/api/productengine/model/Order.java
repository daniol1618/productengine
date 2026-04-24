package com.api.productengine.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    private String description;

    @Column(nullable = false)
    private Integer quantity;

    // Constructors
    public Order() {}

    public Order(Long productId, String description,  Integer quantity) {
        this.productId = productId;
        this.description = description;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public Long getProductId() { return productId; }

    public void setProductId(Long productId) { this.productId = productId; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
