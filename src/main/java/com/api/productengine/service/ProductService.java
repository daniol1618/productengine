package com.api.productengine.service;

import com.api.productengine.exceptions.ExcepcionProductoNoEncontrado;
import com.api.productengine.model.Producto;
import com.api.productengine.repository.ProductRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Producto create(Producto producto) {
        producto.setName("Garbage"+"545645645");
        return repository.save(producto);
    }

    public List<Producto> findAll() {
        return repository.findAll();
    }

    public Producto findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Producto update(Long id, Producto updated) {
        Producto existing = findById(id);

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Producto> searchProducts(String nombre, Double precioMax) {
        return this.repository.searchProducts(nombre, precioMax);
    }

    public Double findTotalStockValue() {
        return this.repository.findTotalStockValue();
    }

    public int updateProductStock(Long id, Integer newStock) {
        return this.repository.updateProductStock(id, newStock);
    }

    public BigDecimal findAveragePrice() {
        return this.repository.findAveragePrice();
    }

    public List<Producto> findByPriceRange(BigDecimal min, BigDecimal max) {
        return this.repository.findByPriceRange(min, max);
    }

    public List<Producto> findOutOfStockProducts() {
        return this.repository.findOutOfStockProducts();
    }

    public List<Producto> findByNameCaseInsensitive(String name) {
        return this.repository.findByNameCaseInsensitive(name);
    }
}
