package com.api.productengine.controller;

import com.api.productengine.exception.ErrorResponse;
import com.api.productengine.model.Product;
import com.api.productengine.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> getLowStock(@RequestParam(defaultValue = "10") int threshold) {
        return ResponseEntity.ok(service.findLowStock(threshold));
    }

    @GetMapping("/total-value")
    public ResponseEntity<java.math.BigDecimal> getDiscountedTotal(@RequestParam(defaultValue = "0") double discount) {
        return ResponseEntity.ok(service.calculateDiscountedTotalValue(discount));
    }

    @GetMapping("/report")
    public ResponseEntity<List<String>> getInventoryReport() {
        return ResponseEntity.ok(service.getFormattedInventoryReport());
    }

    @GetMapping("/most-expensive")
    public ResponseEntity<Product> getMostExpensive() {
        Product product = service.getMostExpensiveProductInStock();
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
}