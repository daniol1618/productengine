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

    @GetMapping("/{name}")
    public ResponseEntity<List<Product>> getByName(@PathVariable String name) {
        List<Product> products = service.findByNameCaseInsensitive(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchByMaxPrice(
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "maxPrice", defaultValue = "1000000.0") Double maxPrice) 
        {
        List<Product> products = service.searchProductsByMaxPrice(keyword, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotalStockValue()
        {
        Double total = service.getTotalStockValue();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/avg")
    public ResponseEntity<BigDecimal> getAvgPrice()
        {
        BigDecimal average = service.findAveragePrice();
        return ResponseEntity.ok(average);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getBetweenPrice(
        @RequestParam BigDecimal min, @RequestParam BigDecimal max){
            List<Product> products = service.findByPriceRange(min, max);
            return ResponseEntity.ok(products);
    }
    
    @GetMapping("/stock/0")
    public ResponseEntity<List<Product>> findOutOfStock(){
            List<Product> products = service.findOutOfStockProducts();
            return ResponseEntity.ok(products);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        Product updated = service.update(id, product);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/stock")
    public ResponseEntity<Integer> updateStock(@RequestParam Long id, @RequestParam Integer newStock) {
        int updated = service.updateProductStock(id, newStock);
        return ResponseEntity.ok(updated);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
