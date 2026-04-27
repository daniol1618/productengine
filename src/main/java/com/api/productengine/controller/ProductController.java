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

    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam String keyword, @RequestParam Double maxPrice) {
        List<Product> products = service.findProducts(keyword, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/stock-value")
    public ResponseEntity<Double> getTotalValue() {
        Double totalValue = service.getTotalStockValue();
        return ResponseEntity.ok(totalValue);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<String> updateStock(@PathVariable Long id, @RequestParam Integer newStock) {
        boolean updated = service.updateStock(id, newStock);
        if (updated) {
            return ResponseEntity.ok("Stock updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("No se encontró el producto con ID: " + id);
        }
    }

    @GetMapping("/average-price")
    public ResponseEntity<BigDecimal> getAveragePrice() {
        BigDecimal averagePrice = service.getAveragePrice();
        return ResponseEntity.ok(averagePrice);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getByPriceRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        List<Product> products = service.getProductsByPriceRange(min, max);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/out-of-stock")
    public ResponseEntity<List<Product>> getOutOfStockProducts() {
        List<Product> products = service.getOutOfStockProducts();

        if(products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(products);
    }

    @GetMapping("/search-insensitive")
    public ResponseEntity<List<Product>> searchCaseInsensitive(@RequestParam String name) {
        List<Product> products = service.getByNameCaseInsensitive(name);

        if(products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(products);
    }
}
