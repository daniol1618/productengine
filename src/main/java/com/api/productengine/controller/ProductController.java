package com.api.productengine.controller;

import com.api.productengine.exception.ErrorResponse;
import com.api.productengine.exception.ProductNotFoundException;
import com.api.productengine.model.Product;
import com.api.productengine.service.ProductService;
import org.springframework.data.repository.query.Param;
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
    public List<Product> search(@RequestParam String keyword, @RequestParam Double maxPrice) {
        return service.search(keyword, maxPrice);
    }

    @GetMapping("/total")
    public Double total() {
        return service.findTotalStockValue();
    }

    @PutMapping("/updateStock")
    public int updateStock(@RequestParam Long id, @Param("newStock") Integer newStock) {
        return service.updateProductStock(id, newStock);
    }

    @GetMapping("/avgPrice")
    public BigDecimal averagePrice() {
        return service.findAveragePrice();
    }

    @PutMapping("/intervalPrice")
    public List<Product> findByPriceRange(@Param("min") BigDecimal min, @Param("max") BigDecimal max) {
        return service.findByPriceRange(min, max);
    }

}
