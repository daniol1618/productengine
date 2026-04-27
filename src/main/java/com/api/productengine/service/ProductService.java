package com.api.productengine.service;

import com.api.productengine.exception.ProductNotFoundException;
import com.api.productengine.model.Product;
import com.api.productengine.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

    public List<Product> findProducts(String keyword, Double maxPrice) {
        return repository.searchProducts(keyword, maxPrice);
    }

    public Double getTotalStockValue() {
        Double totalValue = repository.findTotalStockValue();
        return totalValue != null ? totalValue : 0.0;
    }

    public boolean updateStock(Long id, Integer newStock) {
        int rowsAffected = repository.updateProductStock(id, newStock);
        return rowsAffected > 0;
    }

    public BigDecimal getAveragePrice() {
        BigDecimal average = repository.findAveragePrice();
        return average != null ? average : BigDecimal.ZERO;
    }

    public List<Product> getProductsByPriceRange(BigDecimal min, BigDecimal max) {
        if (min != null && max != null && min.compareTo(max) > 0) {
            throw new IllegalArgumentException("Min price cannot be greater than max price");
        }
        return repository.findByPriceRange(min, max);
    }

    public List<Product> getOutOfStockProducts() {
        return repository.findOutOfStockProducts();
    }

    public List<Product> getByNameCaseInsensitive(String name) {
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name parameter cannot be null or empty");
        }
        
        return repository.findByNameCaseInsensitive(name);
    }
}
