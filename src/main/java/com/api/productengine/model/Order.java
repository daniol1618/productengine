package com.api.productengine.model;

import jakarta.persistence.*;
        import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private BigDecimal price;


    // Constructors
    public Order() {}

    public Order(String name, Product product) {
        this.name = name;
        this.product = product;
        this.price = product.getPrice();
    }

    // Getters and Setters
    public Long getId() {
         return id; 
    }

    public String getName() {
         return name; 
    }

    public void setName(String name) {
         this.name = name;
    }

    public BigDecimal getPrice() {
         return price; 
    }

    public void setPrice(BigDecimal price) {
        this.price = price; 
    }

    public Product getProduct() { 
        return product; 
    }

    public void setProduct(Product product) { 
        this.product = product; 
    }

}