package com.api.productengine.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    public Order(Product product, Integer quantity){
        this.product = product;
        this.quantity = quantity;
        this.creationDate = LocalDate.now();
    }

    public Product getProduct(){
        return product;
    }

    public LocalDate getCreationDate(){
        return creationDate;
    }
}
