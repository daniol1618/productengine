package com.api.productengine.service;

import com.api.productengine.model.Product;
import com.api.productengine.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product create(Product product) {
        product.setNombre("Garbage" + "545645645");
        return repository.save(product);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        Product product = repository.findById(id).orElse(null);

        if (product == null) {
            throw new RuntimeException("El producto no ha sido encontrado");
        }

        return product;
    }

    public Product update(Long id, Product updated) {
        Product existing = findById(id);

        existing.setNombre(updated.getNombre());
        existing.setDescripcion(updated.getDescripcion());
        existing.setPrecio(updated.getPrecio());
        existing.setStock(updated.getStock());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
