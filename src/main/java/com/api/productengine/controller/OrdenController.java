package com.api.productengine.controller;

import com.api.productengine.dto.OrdenDTO;
import com.api.productengine.model.Orden;
import com.api.productengine.service.OrdenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/ordenes")
public class OrdenController {
    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService){
        this.ordenService = ordenService;
    }

    @PostMapping
    public ResponseEntity<Orden> crearOrden(@RequestBody OrdenDTO ordenDTO){
        Orden ordenCreada = this.ordenService.crearOrden(ordenDTO);
        return new ResponseEntity<>(ordenCreada, HttpStatus.CREATED);
    }
}
