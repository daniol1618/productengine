package com.api.productengine.controller;

import com.api.productengine.model.Product;
import com.api.productengine.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Double precioMaximo) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre, precioMaximo));
    }

    @GetMapping("/stock-value")
    public ResponseEntity<Double> valorStock() {
        return ResponseEntity.ok(service.valorStock());
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Integer> actualizarStock(@PathVariable Long id, @RequestParam Integer nuevoStock) {
        return ResponseEntity.ok(service.actualizarStock(id, nuevoStock));
    }

    @GetMapping("/average-price")
    public ResponseEntity<Double> precioPromedio() {
        return ResponseEntity.ok(service.precioPromedio());
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> buscarPorRangoPrecio(
            @RequestParam Double precioMinimo,
            @RequestParam Double precioMaximo) {
        return ResponseEntity.ok(service.buscarPorRangoPrecio(precioMinimo, precioMaximo));
    }

    @GetMapping("/out-of-stock")
    public ResponseEntity<List<Product>> productosAgotados() {
        return ResponseEntity.ok(service.productosAgotados());
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<List<Product>> buscarPorNombreCaseInsensitive(@RequestParam String nombre) {
        return ResponseEntity.ok(service.buscarPorNombreCaseInsensitive(nombre));
    }

}
