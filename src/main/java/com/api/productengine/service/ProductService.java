package com.api.productengine.service;

import com.api.productengine.dto.ProductDTO;
import com.api.productengine.exception.ProductNotFoundException;
import com.api.productengine.model.Product;
import com.api.productengine.repository.ProductRepository;
import jakarta.transaction.Transactional;
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

    // 1. JPQL with Named Parameters -> busqueda de productos por maximo precio
    public List<ProductDTO> searchProducts(String keyword, Double maxPrice) {
        return repository.searchProducts(keyword, maxPrice)
                .stream().map(this::mapToDTO).toList();
    }

    // 2. Native Query (Standard SQL) -> valor total del stock
    public Double getTotalStockValue() {
        return repository.findTotalStockValue();
    }

    // 3. Modifying Query (Update) -> actualizar stock
    @Transactional
    public int updateStock(Long id, Integer newStock) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        return repository.updateProductStock(id, newStock);
    }

    // 4. Aggregate JPQL -> precio promedio de productos
    public BigDecimal getAveragePrice() {
        return repository.findAveragePrice();
    }

    // 5. JPQL with BETWEEN operator -> productos entre rango de precio
    public List<ProductDTO> getProductsByPriceRange(BigDecimal min, BigDecimal max) {
        return repository.findByPriceRange(min, max)
                .stream().map(this::mapToDTO).toList();
    }

    // 6. JPQL with literal values -> productos sin stock
    public List<ProductDTO> getOutOfStockProducts() {
        return repository.findOutOfStockProducts()
                .stream().map(this::mapToDTO).toList();
    }

    // 7. JPQL with String functions -> productos por nombre
    public List<ProductDTO> findByNameIgnoreCase(String name) {
        return repository.findByNameCaseInsensitive(name)
                .stream().map(this::mapToDTO).toList();
    }

    // mapear entidad a DTO
    private ProductDTO mapToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        return dto;
    }
}
