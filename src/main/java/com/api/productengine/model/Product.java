package com.api.productengine.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Schema(description = "Represents a product in the system")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the product", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @Column(nullable = false)
    @Schema(description = "Name of the product", example = "Laptop Pro")
    private String name;
    
    @Schema(description = "Brief description of the product", example = "High-performance laptop for professionals")
    private String description;
    
    @Column(nullable = false)
    @Schema(description = "Price of the product", example = "1299.99")
    private BigDecimal price;
    
    @Column(nullable = false)
    @Schema(description = "Available stock quantity", example = "50")
    private Integer stock;

    // Constructors
    public Product() {}

    public Product(String name, String description, BigDecimal price, Integer stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock; }

    public void setStock(Integer stock) { this.stock = stock; }
}