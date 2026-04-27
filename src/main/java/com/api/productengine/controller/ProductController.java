package com.api.productengine.controller;

import com.api.productengine.dto.ProductDTO;
import com.api.productengine.dto.ProductStockDTO;
import com.api.productengine.model.Product;
import com.api.productengine.service.ProductService;

import jakarta.annotation.Nonnull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDTO productDto) {
        Product created = service.create(productDto);
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
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody ProductDTO productDto) {
        Product updated = service.update(id, productDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Double> getTotalStockValue() {
        Double totalStockValue = service.getTotalStockValue();
        return ResponseEntity.ok(totalStockValue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateStock(@PathVariable Long id, @RequestBody ProductStockDTO productStockDto) {
        Product updated = service.updateStock(id, productStockDto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<BigDecimal> getAveragePrice() {
        BigDecimal averagePrice = service.getAveragePrice();
        return ResponseEntity.ok(averagePrice);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findOutOfStock() {
        List<Product> outOfStockProducts = service.findOutOfStock();
        return ResponseEntity.ok(outOfStockProducts);
    }

    @GetMapping("/keyword")
    public ResponseEntity<List<Product>> findByKeywordAndMaxPrice(
            @RequestParam @Nonnull String keyword,
            @RequestParam @Nonnull Double maxPrice) {
        
        List<Product> products = service.findByKeywordAndMaxPrice(keyword, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findByPriceRange(
            @RequestParam @Nonnull BigDecimal minPrice,
            @RequestParam @Nonnull BigDecimal maxPrice) {

        List<Product> products = service.findByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Product>> searchByName(
            @RequestParam String name) {
        
        List<Product> products = service.findByName(name);
        return ResponseEntity.ok(products);
    }
}
