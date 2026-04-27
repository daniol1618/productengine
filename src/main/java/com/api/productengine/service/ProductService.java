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

    public List<Product> searchProductsByMaxPrice(String keyword, Double maxPrice) {
        return repository.searchProductsByMaxPrice(keyword, maxPrice);
    }

    public Double findTotalStockValue() {
        return repository.findTotalStockValue();
    }

    public int updateStock(Long id, int stock) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        return repository.updateProductStock(id, stock);
    }

    public BigDecimal findAveragePrice() {
        return repository.findAveragePrice();
    }

    public List<Product> findByPriceRange(BigDecimal min, BigDecimal max) {
        return repository.findByPriceRange(min, max);
    }

    public List<Product> findOutOfStockProducts() {
        return repository.findOutOfStockProducts();
    }

    public List<Product> findByNameCaseInsensitive(String name) {
        return repository.findByNameCaseInsensitive(name);
    }
}
