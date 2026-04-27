package com.api.productengine.service;

import com.api.productengine.model.Product;
import com.api.productengine.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product product) {
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product price must be greater than 0");
        }

        if (product.getStock() == null || product.getStock() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product stock must be greater than 0 to be added");
        }
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found");
        }
        return products;
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    public Product update(Long id, Product updated) {
        Product existing = findById(id);

        if (updated.getPrice() == null || updated.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price must be greater than 0");
        }

        if (updated.getStock() == null || updated.getStock() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product stock must be greater than 0 to be added");
        }

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());

        return productRepository.save(existing);
    }

    public void delete(Long id) {
        Product existing = findById(id); //Valida que exista con metodo anterior
        productRepository.delete(existing);
    }
}
