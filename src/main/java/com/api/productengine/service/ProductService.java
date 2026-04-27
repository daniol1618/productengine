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

    public List<Product> searchProductsByNameAndMaxPrice(String name, BigDecimal maxPrice){
        return this.repository.searchProducts(name,maxPrice.doubleValue());
    }

    public Double findTotalStockValue(){
        return this.repository.findTotalStockValue();
    }

    public int updateStock(Long id, Integer newStock){
        return this.repository.updateProductStock(id,newStock);
    }

    public BigDecimal getAveragePrice(){
        return this.repository.findAveragePrice();
    }

    public List<Product> getByPriceRange(BigDecimal min, BigDecimal max){
        return this.repository.findByPriceRange(min,max);
    }

    public List<Product> getOutOfStockProducts(){
        return this.repository.findOutOfStockProducts();
    }

    public List<Product> findByCaseInsensitive(String name){
        return this.repository.findByNameCaseInsensitive(name);
    }
}
