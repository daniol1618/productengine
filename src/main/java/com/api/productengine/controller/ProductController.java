package com.api.productengine.controller;

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

    @GetMapping("/search/by-max-price")
    public ResponseEntity<List<Product>> getAllWithMaxPrice(@RequestParam String keyword, @RequestParam Double maxPrice) {
        List<Product> products = service.findAllWithMaxPrice(keyword, maxPrice);
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

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long id, @RequestParam Integer newStock) {
        Product updated = service.updateStock(id, newStock);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats/total-value")
    public ResponseEntity<Double> getTotalStockValue() {
        Double total= service.findTotalStockValue();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/stats/average-price")
    public ResponseEntity<BigDecimal> getAveragePrice() {
        BigDecimal avg= service.findAveragePrice();
        return ResponseEntity.ok(avg);
    }

    @GetMapping("/search/by-price-range")
    public ResponseEntity<List<Product>> getAllByPriceRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        List<Product> products = service.findAllByPriceRange(min, max);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/out-of-stock") //o hacer "/search?stock=0"?
    public ResponseEntity<List<Product>> getAllOutOfStock() {
        List<Product> products = service.findAllOutOfStock();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/by-name")
    public ResponseEntity<List<Product>> getAllByNameCaseInsensitive(@RequestParam String keyword) {
        List<Product> products = service.findAllByNameCaseInsensitive(keyword);
        return ResponseEntity.ok(products);
    }



}
