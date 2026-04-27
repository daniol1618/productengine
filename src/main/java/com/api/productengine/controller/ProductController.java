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

    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam String keyWord, @RequestParam Double maxPrice){
        List<Product> products = service.serchProducts(keyWord, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/totalvalue")
    public ResponseEntity<Double> findTotalStockValue(){
        Double totalValue = service.findTotalStockValue();
        return ResponseEntity.ok(totalValue);
    }

    @PutMapping("stock/{id]")
    public ResponseEntity<Integer> updateStock(@PathVariable Long id, @RequestBody Integer newStock){
        Integer stock = service.updateStock(id, newStock);
        return ResponseEntity.ok(stock);
    }

    @GetMapping("/averageprice")
    public ResponseEntity<BigDecimal> getAveragePrice(){
        BigDecimal avgPrice = service.getAveragePrice();
        return ResponseEntity.ok(avgPrice);
    }

    @GetMapping("/serch/price/{min}/{max}")
    public ResponseEntity<List<Product>> findByPriceRange(@PathVariable BigDecimal min, @PathVariable BigDecimal max){
        List<Product> products = service.findByPricingRange(min, max);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/outofstock")
    public ResponseEntity<List<Product>> findOutOfStock(){
        List<Product> products = service.findOutOfStockProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/name/nocase/{name}")
    public ResponseEntity<List<Product>> findByNameCaseInsensitive(@PathVariable String name){
        List<Product> products = service.findByNameNoCaseSensitive(name);
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


}
