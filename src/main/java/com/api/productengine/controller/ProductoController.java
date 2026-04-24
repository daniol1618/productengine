package com.api.productengine.controller;

import com.api.productengine.dto.ProductoDTO;
import com.api.productengine.model.Producto;
import com.api.productengine.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    public Producto create(@RequestBody ProductoDTO product) {
        return productoService.create(product);
    }

    @GetMapping
    public List<Producto> getAll() {
        System.out.println("exposed dta");
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public Producto getById(@PathVariable Long id) {
        return productoService.findById(id);
    }

    @PutMapping("/{id}")
    public Producto update(@PathVariable Long id, @RequestBody Producto product) {
        return productoService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productoService.delete(id);
    }
}