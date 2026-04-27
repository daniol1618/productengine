package com.api.productengine.dto;

import java.math.BigDecimal;

public class OrderDTO {
    private Long id;
    private Long productId;
    private BigDecimal total;

    //Constructores
    public OrderDTO(Long id, Long productId, BigDecimal total) {
        this.id = id;
        this.productId = productId;
        this.total = total;
    }

    public OrderDTO() {}

    //Setter y Getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
