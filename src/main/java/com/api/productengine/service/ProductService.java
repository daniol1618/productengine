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

    public Double getTotalStockValue() {
        return repository.findTotalStockValue();
    }

    public BigDecimal getAverageValue() {
        return repository.findAveragePrice();
    }

    public List<Product> findAll(String name, String keyword, Double maxPrice, Double minPrice, Integer stock) {
        if(name != null && !name.isEmpty()) {
            return repository.findByNameContainingIgnoreCase(name);
        }
        if(keyword != null && !keyword.isEmpty()) {
            return repository.searchProducts(keyword, maxPrice != null ? maxPrice : Double.MAX_VALUE);
        }
        if(minPrice != null && maxPrice != null) {
            return repository.findByPriceRange(BigDecimal.valueOf(minPrice), BigDecimal.valueOf(maxPrice));
        }
        if(stock != null && stock == 0) {
            return repository.findOutOfStockProducts();
        }
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
}
