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

    //1
    @GetMapping("/search")
    public ResponseEntity<List<Product>> getByKeywordAndMaxPrice(@RequestParam String keyword, @RequestParam Double maxPrice) {
        var products = this.service.findProductByKeywordAndMaxPrice(keyword, maxPrice);
        return ResponseEntity.ok(products);
    }

    //2
    @GetMapping("/stock-value")
    public ResponseEntity<Double> getStockValue() {
        return ResponseEntity.ok(this.service.findTotalStockValue());
    }

    //3
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long id, @RequestParam Integer stock) {
        return ResponseEntity.ok(this.service.updateProductStock(id, stock));
    }

    //4
    @GetMapping("/avg-price")
    public ResponseEntity<BigDecimal> getAveragePrice() {
        return ResponseEntity.ok(this.service.findAveragePrice());
    }

    //5
    @GetMapping("/range")
    public ResponseEntity<List<Product>> getProductsByrange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return ResponseEntity.ok(this.service.findProductsByPriceRange(min, max));
    }

    //6
    @GetMapping("/out-of-stock")
    public ResponseEntity<List<Product>> getProductsOutOfStock() {
        return ResponseEntity.ok(this.service.findOutOfStockProducts());
    }

    //7
    @GetMapping("/name")
    public ResponseEntity<List<Product>> getProductsByName(@RequestParam String name) {
        return ResponseEntity.ok(this.service.findProductsByNameCaseInsensitive(name));
    }
}