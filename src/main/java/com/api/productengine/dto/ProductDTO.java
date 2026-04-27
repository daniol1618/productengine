package com.api.productengine.dto;

import java.math.BigDecimal;

public record ProductDTO (
     String name,
     String description,
     BigDecimal price,
     Integer stock

){

}
