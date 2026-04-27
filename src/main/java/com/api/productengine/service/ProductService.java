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
    // 1
    public List<Product> search(String keyword, BigDecimal maxPrice) {
        return repository.searchProducts(keyword, maxPrice);
    }
    // 2
    public double getTotalInventoryValue() {
        return repository.findTotalStockValue();
    }
    // 3
    public void updateStock(Long id, Integer newStock) {
        int rowsAffected = repository.updateProductStock(id, newStock);
        if (rowsAffected == 0) {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }
    // 4  
    public BigDecimal getAveragePrice() {
    return repository.findAveragePrice();
    }
    // 5
    public List<Product> getProductsByRange(BigDecimal min, BigDecimal max) {
        return repository.findByPriceRange(min, max);
    }
    // 6
    public List<Product> getOutOfStock() {
        return repository.findOutOfStockProducts();
    }
    // 7
    public List<Product> getByNameInsensitive(String name) {
        return repository.findByNameCaseInsensitive(name);
    }
}

