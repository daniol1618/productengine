package com.api.productengine.service;

import com.api.productengine.exception.ProductNotFoundException;
import com.api.productengine.model.Product;
import com.api.productengine.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import java.util.Comparator;

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

    public List<Product> findLowStock(int threshold) {
        return repository.findAll().stream()
                .filter(product -> product.getStock() < threshold)
                .toList();
    }

    /**
     * Operation 1: Calculates the total value of the inventory after applying a discount, 
     * excluding products that are currently out of stock.
     */
    public BigDecimal calculateDiscountedTotalValue(double discountPercentage) {
        double discountFactor = 1 - (discountPercentage / 100);

        return repository.findAll().stream()
                .filter(product -> product.getStock() > 0)
                .map(product -> product.getPrice()
                        .multiply(BigDecimal.valueOf(discountFactor))
                        .multiply(BigDecimal.valueOf(product.getStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Operation 2: Generates a sorted inventory report as a list of strings.
     */
    public List<String> getFormattedInventoryReport() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER))
                .map(product -> String.format("Product: %s | Stock: %d | Price: $%.2f", 
                        product.getName(), product.getStock(), product.getPrice()))
                .toList();
    }

    /**
     * Operation 3: Finds the most expensive product that is currently available in stock.
     */
    public Product getMostExpensiveProductInStock() {
        return repository.findAll().stream()
                .filter(product -> product.getStock() > 0)
                .max(Comparator.comparing(Product::getPrice))
                .orElse(null);
    }
}
