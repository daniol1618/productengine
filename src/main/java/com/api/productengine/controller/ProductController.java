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

    @GetMapping("/searchProducts")
    public ResponseEntity<List<Product>> search(@RequestParam String keyword, @RequestParam Double maxPrice) {
        List<Product> results = service.searchProducts(keyword, maxPrice);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/stock")
    public ResponseEntity<Double> getTotalStockValue() {
        Double totalValue = service.findTotalStockValue();
        return ResponseEntity.ok(totalValue);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Void> updateStock(@PathVariable Long id, @RequestParam Integer newStock ) {
        int rowsAffected = service.updateProductStock(id, newStock);
        if (rowsAffected > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/average-price")
    public ResponseEntity<Double> getAveragePrice() {
        Double averagePrice = service.findAveragePrice();
        return ResponseEntity.ok(averagePrice);
    }   

    @GetMapping("/searchByPrice")
    public ResponseEntity<List<Product>> getByPriceRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        List<Product> products = service.findByPriceRange(min, max);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/out-of-stock")
    public ResponseEntity<List<Product>> getOutOfStockProducts() {
        List<Product> products = service.findOutOfStockProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/searchByName")
    public ResponseEntity<List<Product>> getByNameCaseInsensitive(@RequestParam String name) {
        List<Product> products = service.findByNameCaseInsensitive(name);
        return ResponseEntity.ok(products);
    }   
}
