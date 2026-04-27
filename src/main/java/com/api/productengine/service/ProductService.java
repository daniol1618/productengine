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
    public List<Product> searchProducts(String a, Double b){
        return repository.searchProducts(a, b);
    }
    //2
    public Double getTotalValue(){
        return repository.findTotalStockValue();
    }
    //3
    public void updateStock(Long id, Integer newStock){
        repository.updateProductStock(id, newStock);
    }
    //4
    public BigDecimal getAveragePrice (){
        return repository.findAveragePrice();
    }
    //5
    public List<Product> findByRange(BigDecimal min, BigDecimal max){
        return repository.findByPriceRange(min, max);
    }
    //6
    public List<Product> getOutOfStock(){
        return repository.findOutOfStockProducts();
    }
    //7
    public List<Product> findByNameInsensitive(String name){
        return repository.findByNameCaseInsensitive(name);
    }
}

