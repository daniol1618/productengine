package com.api.productengine.controller;

import com.api.productengine.dto.product.CreateProductRequestDTO;
import com.api.productengine.dto.product.ProductResponseDTO;
import com.api.productengine.dto.product.UpdateProductRequestDTO;
import com.api.productengine.service.ProductService;
import com.api.productengine.service.impl.ProductServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ProductResponseDTO create(@RequestBody CreateProductRequestDTO product) {
        return service.create(product);
    }

    @GetMapping
    public List<ProductResponseDTO> getAll() {
        System.out.println("exposed dta");
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable("id") Long id, @RequestBody UpdateProductRequestDTO product) {
        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}