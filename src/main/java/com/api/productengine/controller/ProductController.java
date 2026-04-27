package com.api.productengine.controller;

import com.api.productengine.exception.ErrorResponse;
import com.api.productengine.exception.ProductNotFoundException;
import com.api.productengine.model.Product;
import com.api.productengine.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

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

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword, @RequestParam Double maxPrice) {
        return service.searchProducts(keyword, maxPrice);
    }

    @GetMapping("/stock-value")
    public Double findTotalStockValue() {
        return service.findTotalStockValue();
    }

    @PutMapping("/{id}/stock")
    public int updateProductStock(@PathVariable Long id, @RequestParam Integer newStock) {
        return service.updateProductStock(id, newStock);
    }

    @GetMapping("/average-price")
    public BigDecimal findAveragePrice() {
        return service.findAveragePrice();
    }

    @GetMapping("/price-range")
    public List<Product> findByPriceRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return service.findByPriceRange(min, max);
    }

    @GetMapping("/out-of-stock")
    public List<Product> findOutOfStockProducts() {
        return service.findOutOfStockProducts();
    }

    @GetMapping("/search/name")
    public List<Product> findByNameCaseInsensitive(@RequestParam String name) {
        return service.findByNameCaseInsensitive(name);
    }
}
