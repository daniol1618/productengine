package com.api.productengine.controller;

import com.api.productengine.dto.CreateProductRequestDTO;
import com.api.productengine.dto.ProductResponseDTO;
import com.api.productengine.dto.UpdateProductRequestDTO;
import com.api.productengine.model.Product;
import com.api.productengine.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product create(@RequestBody CreateProductRequestDTO product) {
        return service.create(product);
    }

    @GetMapping
    public List<ProductResponseDTO> getAll() {
        System.out.println("exposed dta");
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable Long id, @RequestBody UpdateProductRequestDTO product) {
        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}