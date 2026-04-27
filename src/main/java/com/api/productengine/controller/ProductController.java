package com.api.productengine.controller;

import com.api.productengine.exception.ErrorResponse;
import com.api.productengine.exception.ProductNotFoundException;
import com.api.productengine.model.Product;
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
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product created = service.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = service.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Product product = service.findById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        Product updated = service.update(id, product);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats/average-price")
    public ResponseEntity<BigDecimal> getAveragePrice() {
        return ResponseEntity.ok(service.getAveragePrice());
    }

    @GetMapping("/stats/total-stock-value")
    public ResponseEntity<Double> getTotalStockValue() {
        return  ResponseEntity.ok(service.getTotalStockValue());
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long id, @RequestParam Integer newStock) {
        return ResponseEntity.ok(service.updateStock(id, newStock));
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<Product>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(service.findByNameContainingIgnoreCase(name));
    }

    @GetMapping(params = {"name", "maxPrice"})
    public ResponseEntity<List<Product>> searchByNameAndPrice(
            @RequestParam String name,
            @RequestParam Double maxPrice) {
        return ResponseEntity.ok(service.searchByNameAndPrice(name, maxPrice));
    }

    @GetMapping(params = {"minPrice", "maxPrice"})
    public ResponseEntity<List<Product>> getByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(service.findByPriceRange(minPrice, maxPrice));
    }

    @GetMapping(params = "outOfStock")
    public ResponseEntity<List<Product>> getOutOfStock() {
        return ResponseEntity.ok(service.findOutOfStock());
    }


}
