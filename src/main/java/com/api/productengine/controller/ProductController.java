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

    @GetMapping("/search/max-price")
    // http://localhost:8081/api/products/search/max-price?keyword=Garbage&maxPrice=3000
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword, @RequestParam Double maxPrice) {
        List<Product> products = service.searchProductsByMaxPrice(keyword, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/price-range")
    // http://localhost:8081/api/products/search/price-range?min=100&max=5000
    public ResponseEntity<List<Product>> findByPriceRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        List<Product> products = service.findByPriceRange(min, max);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/stock/out-of-stock")
    // http://localhost:8081/api/products/stock/out-of-stock
    public ResponseEntity<List<Product>> findOutOfStockProducts() {
        List<Product> products = service.findOutOfStockProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/name-insensitive")
    // http://localhost:8081/api/products/search/name-insensitive?name=GARBAGE
    public ResponseEntity<List<Product>> findByNameCaseInsensitive(@RequestParam String name) {
        List<Product> products = service.findByNameCaseInsensitive(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/stock/total-stock-value")
    // http://localhost:8081/api/products/stock/total-stock-value
    public ResponseEntity<Double> getTotalStockValue() {
        Double totalStockValue = service.findTotalStockValue();
        return ResponseEntity.ok(totalStockValue);
    }

    @PutMapping("/stock/update")
    // http://localhost:8081/api/products/stock/update?id=1&stock=0
    public ResponseEntity<Integer> updateStock(@RequestParam Long id, @RequestParam int stock) {
        int updated = service.updateStock(id, stock);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/stats/average-price")
    // http://localhost:8081/api/products/stats/average-price
    public ResponseEntity<BigDecimal> getAveragePrice() {
        BigDecimal averagePrice = service.findAveragePrice();
        return ResponseEntity.ok(averagePrice);
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
