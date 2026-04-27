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
    // 1
    @GetMapping("/search")
    public List<Product> search(@RequestParam String keyword, @RequestParam BigDecimal maxPrice) {
        return service.search(keyword, maxPrice);
    }
    // 2
    @GetMapping("/total-value")
    public double getTotalValue() {
        return service.getTotalInventoryValue();
    }
    // 3
    @PatchMapping("/{id}/stock")
    public void updateStock(@PathVariable Long id, @RequestBody Integer newStock) {
        service.updateStock(id, newStock);
    }
    // 4
    @GetMapping("/average-price")
    public BigDecimal getAverage() {
        return service.getAveragePrice();
    }
    // 5
    @GetMapping("/range")
    public List<Product> getByRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return service.getProductsByRange(min, max);
    }
    // 6
    @GetMapping("/out-of-stock")
    public List<Product> getEmptyStock() {
        return service.getOutOfStock();
    }
    // 7
    @GetMapping("/search-case")
    public List<Product> searchCase(@RequestParam String name) {
        return service.getByNameInsensitive(name);
    }
}
