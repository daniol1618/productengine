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
    @OneToOne
    @JoinColumn(name = "product_id")
    // dado que es una relacion 1-1 se decidio crear la FK product_id en Order
    // pero realmente se deberia haber creado una entidad intermendia ya que un producto
    // puede estar en varias ordenes, y una orden puede tener varios productos en la realidad
    private Product product;

    @Column(nullable = false)
    private BigDecimal price;

    public Order() {}

    public Order(Product orderProduct, BigDecimal orderPrice) {
        this.product = orderProduct;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public Integer getOrderProduct() { return product; }

    public void setOrderProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

    public void isOrderValid() {
        if (product == null) throw new IllegalStateException("Order must have a product");
        if (price == null) throw new IllegalStateException("Order must have a price");
        if (price.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalStateException("Order price must be greater than zero");
        return true;
    }
}