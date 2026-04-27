package com.api.productengine.service;

import com.api.productengine.exception.NotFoundException;
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
                .orElseThrow(() -> new NotFoundException());
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
                .orElseThrow(() -> new NotFoundException());
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }
        repository.deleteById(id);
    }

    public List<Product> serchProducts(String keyWord,Double maxPrice){
        return this.repository.searchProducts(keyWord,maxPrice);
    }

    public Double findTotalStockValue(){
        return this.repository.findTotalStockValue();
    }

    public Integer updateStock(Long id,Integer newStock ){
        return this.repository.updateProductStock(id,newStock);
    }

    public BigDecimal getAveragePrice(){
        return this.repository.findAveragePrice();
    }

    public List<Product> findByPricingRange(BigDecimal min,BigDecimal max){
        return this.repository.findByPriceRange(min,max);
    }

    public List<Product> findOutOfStockProducts(){
        return this.repository.findOutOfStockProducts();
    }

    public List<Product> findByNameNoCaseSensitive(String name){
        return this.repository.findByNameCaseInsensitive(name);
    }
}
