package com.api.productengine.service;

import com.api.productengine.exception.ProductNotFoundException;
import com.api.productengine.model.Product;
import com.api.productengine.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product create(Product product) {
        return repository.save(product);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product update(Long id, Product updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setDescription(updated.getDescription());
                    existing.setPrice(updated.getPrice());
                    existing.setStock(updated.getStock());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public List<Product> buscarPorNombre(String nombre, Double precioMaximo) {

        return repository.searchProducts(nombre, precioMaximo);
    }

    public Double valorStock() {
        return repository.findTotalStockValue();
    }

    public int actualizarStock(Long id, Integer nuevoStock) {
        return repository.updateProductStock(id, nuevoStock);
    }

    public Double precioPromedio() {
        return repository.findAveragePrice().doubleValue();
    }

    public List<Product> buscarPorRangoPrecio(Double precioMinimo, Double precioMaximo) {
        return repository.findByPriceRange(BigDecimal.valueOf(precioMinimo), BigDecimal.valueOf(precioMaximo));
    }

    public List<Product> productosAgotados() {
        return repository.findOutOfStockProducts();
    }

    public List<Product> buscarPorNombreCaseInsensitive(String nombre) {
        return repository.findByNameCaseInsensitive(nombre);
    }

}
