package com.api.productengine.dto;

import java.math.BigDecimal;

public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;

    //Constructores
    public ProductDTO(Long id, String name, BigDecimal price, String description, Integer stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    //Es necesario el contructor vacío, crea instancias automáticamente,
    // SPRING lo usa para convertir JSON a objeto
    //@RequestBody necesita el contructor vacío.
    public ProductDTO() {}

    //Setters y Guetters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
