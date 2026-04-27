package com.api.productengine.controller;

import com.api.productengine.model.Orden;
import com.api.productengine.service.OrdenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @PostMapping("/{productoId}")
    public ResponseEntity<Orden> generarOrden(@PathVariable Long productoId) {
        return ResponseEntity.ok(ordenService.crearOrden(productoId));
    }
}