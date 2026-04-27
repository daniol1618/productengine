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
    public ResponseEntity<List<Product>> getAll(
        @RequestParam(name = "name", required = false) String name,
        @RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "maxPrice", required = false) Double maxPrice,
        @RequestParam(name = "minPrice", required = false) Double minPrice,
        @RequestParam(name = "stock", required = false) Integer stock
    ) {
        List<Product> products = service.findAll(name, keyword, maxPrice, minPrice, stock);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/totalValue")
    public ResponseEntity<Double> getTotalValue() {
        Double totalValue = service.getTotalStockValue();
        return ResponseEntity.ok(totalValue);
    }

    @GetMapping("/priceAverage")
    public ResponseEntity<BigDecimal> getAverageValue() {
        BigDecimal averageValue = service.getAverageValue();
        return ResponseEntity.ok(averageValue);
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
}
