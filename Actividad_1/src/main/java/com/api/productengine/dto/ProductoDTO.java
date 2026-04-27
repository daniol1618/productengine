package com.api.productengine.dto;

import java.math.BigDecimal;

public class ProductoDTO {
    
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    
    public ProductoDTO() {
    }

    public String getNombre() {
        return name;
    }

    public void setNombre(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return description;
    }

    public void setDescripcion(String description) {
        this.description = description;
    }

    public BigDecimal getPrecio() {
        return price;
    }

    public void setPrecio(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
