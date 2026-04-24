package com.api.productengine.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ordenes")
public class Orden {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String numeroDeOrden;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Getter
    @Setter
    private BigDecimal precio;

    @Getter
    @Setter
    private LocalDateTime fechaPedido;

    public Orden(){
        this.numeroDeOrden = UUID.randomUUID().toString();
        this.fechaPedido = LocalDateTime.now();
    }
}
