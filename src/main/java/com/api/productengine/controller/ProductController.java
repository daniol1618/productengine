package com.api.productengine.controller;

import com.api.productengine.model.Producto;
import com.api.productengine.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Producto create(@RequestBody Producto producto) {
        return service.create(producto);
    }

    @GetMapping
    public List<Producto> getAll() {
        System.out.println("exposed dta");
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Producto getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Producto update(@PathVariable Long id, @RequestBody Producto producto) {
        return service.update(id, producto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping(value = "/search", params = "outOfStock=true")
    public ResponseEntity<List<Producto>> searchOutOfStock() {
        return ResponseEntity.ok(service.findOutOfStockProducts());
    }

    @GetMapping(value = "/search", params = {"minPriceRange", "maxPriceRange"})
    public ResponseEntity<List<Producto>> searchByPriceRange(
            @RequestParam BigDecimal minPriceRange,
            @RequestParam BigDecimal maxPriceRange) {
        return ResponseEntity.ok(service.findByPriceRange(minPriceRange, maxPriceRange));
    }

    @GetMapping(value = "/search", params = {"keyword", "maxPrice"})
    public ResponseEntity<List<Producto>> searchByKeyword(
            @RequestParam String keyword,
            @RequestParam Double maxPrice) {
        return ResponseEntity.ok(service.searchProducts(keyword, maxPrice));
    }

    @GetMapping(value = "/search", params = "name")
    public ResponseEntity<List<Producto>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(service.findByNameCaseInsensitive(name));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Producto>> getAllSearch() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/stats/total_value")
    public ResponseEntity<Double> getTotalValue(){
        return new ResponseEntity<>(this.service.findTotalStockValue(), HttpStatus.OK);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<String> updateStock(@PathVariable Long id, @RequestParam Integer stock){
        int updatedRows = this.service.updateProductStock(id, stock);
        return updatedRows > 0 ?
                ResponseEntity.ok("Stock actualizado") :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/stats/avg_price")
    public ResponseEntity<BigDecimal> findAveragePrice(){
        return new ResponseEntity<>(this.service.findAveragePrice(), HttpStatus.OK);
    }
}