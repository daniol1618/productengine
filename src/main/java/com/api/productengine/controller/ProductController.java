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
    public List<Product> search(
            @RequestParam String keyword,
            @RequestParam Double maxPrice) {
        return service.searchProducts(keyword, maxPrice);
    }

    // 2
    @GetMapping("/total-stock-value")
    public Double totalStockValue() {
        return service.getTotalStockValue();
    }

    // 3
    @PutMapping("/{id}/stock")
    public int updateStock(
            @PathVariable Long id,
            @RequestParam Integer newStock) {
        return service.updateProductStock(id, newStock);
    }

    // 4
    @GetMapping("/average-price")
    public BigDecimal averagePrice() {
        return service.getAveragePrice();
    }

    // 5
    @GetMapping("/price-range")
    public List<Product> priceRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        return service.getByPriceRange(min, max);
    }

    // 6
    @GetMapping("/out-of-stock")
    public List<Product> outOfStock() {
        return service.getOutOfStock();
    }

    // 7
    @GetMapping("/search-insensitive")
    public List<Product> searchInsensitive(@RequestParam String name) {
        return service.findByNameInsensitive(name);
    }
}
