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
    public List<Product> searchProductsByNameAndMaxPrice(
            @RequestParam("product-name") String productName,
            @RequestParam("product-max-price")BigDecimal productMaxPrice
    ){
        return this.service.searchProductsByNameAndMaxPrice(productName,productMaxPrice);
    }

    @GetMapping("/total-stock-value")
    public Double getTotalStockValue(){
        return this.service.findTotalStockValue();
    }

    @PutMapping("/{id}/stock")
    public int updateProductStock(@PathVariable Long id, @RequestParam("new-stock") Integer newStock){
        return this.service.updateStock(id,newStock);
    }

    @GetMapping("/average-price")
    public BigDecimal getAveragePrice(){
        return this.service.getAveragePrice();
    }

    @GetMapping("/search/price-range")
    public List<Product> getByPriceRange(
            @RequestParam("min-price") BigDecimal minPrice,
            @RequestParam("max-price") BigDecimal maxPrice
    ){
        return this.service.getByPriceRange(minPrice,maxPrice);
    }

    @GetMapping("/search/out-of-stock")
    public List<Product> getOutOfStockProducts(){
        return this.service.getOutOfStockProducts();
    }

    @GetMapping("/search/case-insensitive")
    public List<Product> findByCaseInsensitive(
            @RequestParam("name") String name
    ){
        return this.service.findByCaseInsensitive(name);
    }


}
