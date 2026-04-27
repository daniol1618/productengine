package com.api.productengine.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ordenes")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codigoUnico; // Para diferenciar órdenes

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column
    private Double total;

    public Long getId() {
        return id;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public Producto getProducto() {
        return producto;
    }

    public Double getTotal() {
        return total;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}

// Getters y Setters...