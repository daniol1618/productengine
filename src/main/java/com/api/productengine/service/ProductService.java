package com.api.productengine.service;

import com.api.productengine.exception.ProductNotFoundException;
import com.api.productengine.model.Product;
import com.api.productengine.repository.ProductRepository;
import jakarta.transaction.Transactional;
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

    public List<Product> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> searchByNameAndPrice(String name, Double maxPrice) {
        return repository.searchProducts(name, maxPrice);
    }

    public List<Product> findByPriceRange(BigDecimal min, BigDecimal max) {
        return repository.findByPriceRange(min, max);
    }

    public List<Product> findOutOfStock() {
        return repository.findOutOfStockProducts();
    }

    public List<Product> findByNameCaseInsensitive(String name) {
        return repository.findByNameCaseInsensitive(name);
    }

    @Transactional
    public Product updateStock(Long id, Integer newStock) {
        int updatedRows = repository.updateProductStock(id, newStock);
        if (updatedRows == 0) {
            throw new ProductNotFoundException(id);
        }
        return findById(id);
    }
    public Double getTotalStockValue() {
        Double totalValue = repository.findTotalStockValue();
        return totalValue != null ? totalValue : 0.0;
    }
    public BigDecimal getAveragePrice(){
        BigDecimal avgPrice = repository.findAveragePrice();
        return avgPrice != null ? avgPrice : BigDecimal.ZERO;
    }

}
