package com.api.productengine.service;

import com.api.productengine.exception.ProductNotFoundException;
import com.api.productengine.model.Product;
import com.api.productengine.repository.ProductRepository;
import org.springframework.stereotype.Service;

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
        try {
            Optional<Product> result = repository.findById(id);
            if (result.isEmpty()) {
                throw new ProductNotFoundException(id);
            }
            return result.get();
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while fetching product with id: " + id, e);
        }
    }

    public Product update(Long id, Product updated) {
        try {
            Optional<Product> result = repository.findById(id);
            if (result.isEmpty()) {
                throw new ProductNotFoundException(id);
            }
            Product existing = result.get();
            existing.setName(updated.getName());
            existing.setDescription(updated.getDescription());
            existing.setPrice(updated.getPrice());
            existing.setStock(updated.getStock());
            return repository.save(existing);
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while updating product with id: " + id, e);
        }
    }

    public void delete(Long id) {
        try {
            Optional<Product> result = repository.findById(id);
            if (result.isEmpty()) {
                throw new ProductNotFoundException(id);
            }
            repository.deleteById(id);
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while deleting product with id: " + id, e);
        }
    }
}
