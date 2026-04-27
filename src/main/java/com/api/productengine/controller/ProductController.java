package com.api.productengine.controller;

import com.api.productengine.dto.ProductDTO;
import com.api.productengine.exception.ErrorResponse;
import com.api.productengine.exception.ProductNotFoundException;
import com.api.productengine.model.Product;
import com.api.productengine.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    // 1. Buscar por palabra clave y precio máximo
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> search(@RequestParam String keyword, @RequestParam Double maxPrice) {
        return ResponseEntity.ok(service.searchProducts(keyword, maxPrice));
    }

    // 5. Buscar por rango de precio
    @GetMapping("/range")
    public ResponseEntity<List<ProductDTO>> getByRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return ResponseEntity.ok(service.getProductsByPriceRange(min, max));
    }

    // 6. Ver productos sin stock
    @GetMapping("/out-of-stock")
    public ResponseEntity<List<ProductDTO>> getOutOfStock() {
        return ResponseEntity.ok(service.getOutOfStockProducts());
    }

    // 7. Buscar por nombre (Case Insensitive)
    @GetMapping("/name-check")
    public ResponseEntity<List<ProductDTO>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(service.findByNameIgnoreCase(name));
    }

    // 2 y 4. Estadísticas (Total Value y Average Price)
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(Map.of(
                "totalStockValue", service.getTotalStockValue(),
                "averagePrice", service.getAveragePrice()
        ));
    }

    // 3. Actualización parcial de stock
    @PatchMapping("/{id}/stock")
    public ResponseEntity<String> updateStock(@PathVariable Long id, @RequestParam Integer newStock) {
        int affected = service.updateStock(id, newStock);
        return ResponseEntity.ok("Stock actualizado. Filas afectadas: " + affected);
    }
}
