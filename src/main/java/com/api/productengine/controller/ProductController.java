package com.api.productengine.controller;

import com.api.productengine.model.Product;
import com.api.productengine.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.create(product);
    }

    @GetMapping
    public List<Product> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // --- Custom Queries Endpoints ---

    @GetMapping("/search/name")
    public List<Product> findByNameContaining(@RequestParam String name) {
        return service.findByNameContaining(name);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword, @RequestParam Double maxPrice) {
        return service.searchProducts(keyword, maxPrice);
    }

    @GetMapping("/stock/value")
    public Double findTotalStockValue() {
        return service.findTotalStockValue();
    }

    @PutMapping("/{id}/stock")
    public int updateProductStock(@PathVariable Long id, @RequestParam Integer newStock) {
        return service.updateProductStock(id, newStock);
    }

    @GetMapping("/price/average")
    public java.math.BigDecimal findAveragePrice() {
        return service.findAveragePrice();
    }

    @GetMapping("/price/range")
    public List<Product> findByPriceRange(@RequestParam java.math.BigDecimal min, @RequestParam java.math.BigDecimal max) {
        return service.findByPriceRange(min, max);
    }

    @GetMapping("/stock/empty")
    public List<Product> findOutOfStockProducts() {
        return service.findOutOfStockProducts();
    }

    @GetMapping("/search/name/insensitive")
    public List<Product> findByNameCaseInsensitive(@RequestParam String name) {
        return service.findByNameCaseInsensitive(name);
    }
}