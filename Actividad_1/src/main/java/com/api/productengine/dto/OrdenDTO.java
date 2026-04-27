package com.api.productengine.dto;

public class OrdenDTO {
    
    private Long productoId;

    private Integer cantidad;

    public OrdenDTO() {
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }   
}
