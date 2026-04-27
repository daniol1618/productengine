package com.api.productengine.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private String name;
    private String description;

    private BigDecimal price;

    private Integer stock;
}
