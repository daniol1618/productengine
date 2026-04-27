package com.api.productengine.dto;

import com.api.productengine.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDTO (

     Long productId,

     BigDecimal price
){}
