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

    // Nuevos endpoints

    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(
            @RequestParam String keyword, 
            @RequestParam Double maxPrice) {
        return ResponseEntity.ok(service.search(keyword, maxPrice));
    }

    @GetMapping("/by-name")
    public ResponseEntity<List<Product>> getByName(@RequestParam String name){
        return ResponseEntity.ok(service.findByName(name));
    }
    

    @GetMapping("/range")
    public ResponseEntity<List<Product>> getByPriceRange(
        @RequestParam BigDecimal min,
        @RequestParam BigDecimal max){
            return ResponseEntity.ok(service.findPriceInRange(min, max));
        }
    
    @GetMapping("/out-of-stock")
    public ResponseEntity<List<Product>> getOutOfStock() {
        return ResponseEntity.ok(service.getOutOfStock());
    }

    @GetMapping("/stats/total-value")
    public ResponseEntity<Double> getTotalValue() {
        return ResponseEntity.ok(service.getTotalInventoryValue());
    }

    @GetMapping("/stats/average-price")
    public ResponseEntity<BigDecimal> getAveragePrice() {
        return ResponseEntity.ok(service.getAveragePrice());
    }   

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Void> updateStock(
            @PathVariable Long id, 
            @RequestParam Integer newStock) {
        service.updateStock(id, newStock);
        return ResponseEntity.noContent().build();
    }
}
