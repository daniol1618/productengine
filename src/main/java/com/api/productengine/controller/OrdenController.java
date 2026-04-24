package com.api.productengine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.productengine.dto.OrdenDTO;
import com.api.productengine.model.Orden;
import com.api.productengine.service.OrdenService;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    private final OrdenService ordenService;    

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @PostMapping
    public Orden create(@RequestBody OrdenDTO ordenDTO) {
        return ordenService.create(ordenDTO);
    }

    @GetMapping
    public List<Orden> getAll() {
        return ordenService.findAll();
    }
}
