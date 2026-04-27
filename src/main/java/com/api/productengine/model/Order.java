package com.api.productengine.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private Double saldo;

    public Order(Product product, Double saldo) {
        this.product = product;
        this.saldo = saldo;
    }

    public Order() {}

    public Long getId() {
        return id;
    }

    public Double getSaldo() {
        return saldo;
    }

    public Product getProduct() {
        return product;
    }
}
