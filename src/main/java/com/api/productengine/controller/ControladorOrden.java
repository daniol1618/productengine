package com.api.productengine.controller;

import com.api.productengine.model.Orden;
import com.api.productengine.service.ServicioOrden;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class ControladorOrden {

    private final ServicioOrden servicio;

    public ControladorOrden(ServicioOrden servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/product/{productId}")
    public Orden createOrder(@PathVariable Long productId) {
        return servicio.createOrder(productId);
    }

    @GetMapping
    public List<Orden> getAll() {
        return servicio.findAll();
    }
}