package com.api.productengine.controller;

import com.api.productengine.model.Product;
import com.api.productengine.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService servicio;

    public ProductController(ProductService servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public Product create(@RequestBody Product producto) {
        return servicio.create(producto);
    }

    @GetMapping
    public List<Product> getAll() {
        System.out.println("exposed dta");
        return servicio.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return servicio.findById(id);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product producto) {
        return servicio.update(id, producto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        servicio.delete(id);
    }
}