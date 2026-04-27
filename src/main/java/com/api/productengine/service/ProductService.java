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


    public List<Product> searchProducts(String keyword, Double maxPrice) {
        return repository.searchProducts(keyword, maxPrice);
    }

    public Double getTotalStockValue() {
        return repository.findTotalStockValue();
    }

    public int updateStock(Long id, Integer newStock) {
        return repository.updateProductStock(id, newStock);
    }

    public java.math.BigDecimal getAveragePrice() {
        return repository.findAveragePrice();
    }

    public List<Product> getPriceRange(java.math.BigDecimal min, java.math.BigDecimal max) {
        return repository.findByPriceRange(min, max);
    }

    public List<Product> getOutOfStock() {
        return repository.findOutOfStockProducts();
    }

    public List<Product> getByNameInsensitive(String name) {
        return repository.findByNameCaseInsensitive(name);
    }
}
