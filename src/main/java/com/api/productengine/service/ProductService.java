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
        List<Product> allProducts = repository.findAll();
        BigDecimal totalValue = BigDecimal.ZERO;
        double discountFactor = 1 - (discountPercentage / 100);

        for (Product product : allProducts) {
            // Intermediate Operation: Filter out of stock products
            if (product.getStock() > 0) {
                // Intermediate Operation: Calculate discounted price
                BigDecimal discountedPrice = product.getPrice().multiply(BigDecimal.valueOf(discountFactor));
                BigDecimal productValue = discountedPrice.multiply(BigDecimal.valueOf(product.getStock()));
                
                // Final Operation: Aggregation (Sum)
                totalValue = totalValue.add(productValue);
            }
        }
        return totalValue;
    }

    /**
     * Operation 2: Generates a sorted inventory report as a list of strings.
     */
    public List<String> getFormattedInventoryReport() {
        List<Product> allProducts = repository.findAll();
        
        // Intermediate Operation: Manual Sorting (by name)
        for (int i = 0; i < allProducts.size(); i++) {
            for (int j = i + 1; j < allProducts.size(); j++) {
                if (allProducts.get(i).getName().compareToIgnoreCase(allProducts.get(j).getName()) > 0) {
                    Product temp = allProducts.get(i);
                    allProducts.set(i, allProducts.get(j));
                    allProducts.set(j, temp);
                }
            }
        }

        List<String> report = new java.util.ArrayList<>();
        for (Product product : allProducts) {
            // Final Operation: Transformation and collection
            String line = String.format("Product: %s | Stock: %d | Price: $%.2f", 
                    product.getName(), product.getStock(), product.getPrice());
            report.add(line);
        }
        return report;
    }

    /**
     * Operation 3: Finds the most expensive product that is currently available in stock.
     */
    public Product getMostExpensiveProductInStock() {
        List<Product> allProducts = repository.findAll();
        Product maxProduct = null;

        for (Product product : allProducts) {
            // Intermediate Operation: Filter by stock
            if (product.getStock() > 0) {
                // Final Operation: Find Maximum
                if (maxProduct == null || product.getPrice().compareTo(maxProduct.getPrice()) > 0) {
                    maxProduct = product;
                }
            }
        }
        return maxProduct;
    }
}
