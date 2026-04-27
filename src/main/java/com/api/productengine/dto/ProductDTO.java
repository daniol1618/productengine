package com.api.productengine.dto;

import java.math.BigDecimal;

public class ProductDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;

    public Integer getStock() { return this.stock; }
}
