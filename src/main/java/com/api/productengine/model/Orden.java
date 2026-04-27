
package com.api.productengine.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product producto;

    @Column(nullable = false)
    private BigDecimal total;

    public Orden() {
    }

    public Orden(Product producto, BigDecimal total) {
        this.producto = producto;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return producto;
    }

    public void setProduct(Product product) {
        this.producto = product;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
