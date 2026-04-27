package com.api.productengine.controller;

import com.api.productengine.dto.product.*;
import com.api.productengine.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody CreateProductRequestDTO product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "outOfStock", required = false) Boolean outOfStock
    ) {
        return ResponseEntity.ok(service.findAll(name, minPrice, maxPrice, outOfStock));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable("id") Long id,
            @RequestBody UpdateProductRequestDTO product
    ) {
        return ResponseEntity.ok(service.update(id, product));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductResponseDTO> updateStock(
            @PathVariable("id") Long id,
            @RequestBody UpdateStockRequestDTO newStock
    ) {
        return ResponseEntity.ok(service.updateStock(id, newStock));
    }

    @GetMapping("/stats")
    public ResponseEntity<ProductStatsResponseDTO> getStats(){
        return ResponseEntity.ok(service.findStats());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}