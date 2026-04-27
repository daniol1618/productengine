package com.api.productengine.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="product_id",referencedColumnName="id")
    @JsonBackReference
    private Product product;

    @Column(name="date_time_of_order")
    private LocalDateTime dateAndTimeOfOrder;

    @Column(nullable = false)
    private BigDecimal price;
}
