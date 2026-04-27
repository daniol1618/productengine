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

    //1
    public List<Product> findProductByKeywordAndMaxPrice(String keyword, Double maxPrice) {
        if (keyword.isEmpty() || maxPrice <= 0) throw new IllegalArgumentException();
        return this.repository.searchProducts(keyword, maxPrice);
    }

    //2
    public Double findTotalStockValue() {
        return this.repository.findTotalStockValue();
    }

    //3
    public Product updateProductStock(Long id, Integer newStock) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setStock(newStock);
                    return repository.save(existing);
                })
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    //4
    public BigDecimal findAveragePrice() {
        return this.repository.findAveragePrice();
    }

    //5
    public List<Product> findProductsByPriceRange(BigDecimal min, BigDecimal max) {
        if (min.compareTo(BigDecimal.ZERO) < 1 || max.compareTo(BigDecimal.ZERO) < 1
            || max.compareTo(min) < 1)
            throw new IllegalArgumentException();
        return this.repository.findByPriceRange(min, max);
    }

    //6
    public List<Product> findOutOfStockProducts() {
        return this.repository.findOutOfStockProducts();
    }

    //7
    public List<Product> findProductsByNameCaseInsensitive(String name){
        return this.repository.findByNameCaseInsensitive(name);
    }
}
