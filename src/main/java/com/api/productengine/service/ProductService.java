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

    // 1. JPQL with Named Parameters
    public List<Product> searchProducts(String keyword, Double maxPrice) {
        return repository.searchProducts(keyword, maxPrice);
    }

    // 2. Native Query (Standard SQL)
    public Double getTotalStockValue(){
        return repository.findTotalStockValue();
    }

    // 3. Modifying Query (Update)
    public int updateProductStock(Long id, Integer newStock){
        return repository.updateProductStock(id, newStock);
    }

    // 4. Aggregate JPQL (Average Price)
    public BigDecimal getAveragePrice() {
        return repository.findAveragePrice();
    }

    // 5. JPQL with BETWEEN operator
    public List<Product> getByPriceRange(BigDecimal min, BigDecimal max) {
        return repository.findByPriceRange(min, max);
    }

    // 6 JPQL with literal values
    public List<Product> getOutOfStock() {
        return repository.findOutOfStockProducts();
    }

    // 7 JPQL with String functions
    public List<Product> findByNameInsensitive(String name) {
        return repository.findByNameCaseInsensitive(name);
    }
}
