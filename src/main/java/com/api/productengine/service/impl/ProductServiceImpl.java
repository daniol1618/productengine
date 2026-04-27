package com.api.productengine.service.impl;

import com.api.productengine.dto.product.*;
import com.api.productengine.exception.ResourceNotFoundException;
import com.api.productengine.model.Product;
import com.api.productengine.repository.ProductRepository;
import com.api.productengine.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductResponseDTO create(CreateProductRequestDTO product) {
        validateInput(product);
        Product newProduct = new Product();

        newProduct.setName(product.name());
        newProduct.setDescription(product.description());
        newProduct.setPrice(product.price());
        newProduct.setStock(product.stock());
        return ProductResponseDTO.fromProduct(repository.save(newProduct));
    }

    private void validateInput(CreateProductRequestDTO product) {
        if (product == null) throw new IllegalArgumentException("Product must not be null");
        if (product.name() == null || product.name().isBlank())
            throw new IllegalArgumentException("Product name must not be null or empty");
        if (product.description() == null || product.description().isBlank())
            throw new IllegalArgumentException("Product description must not be null or empty");
        if (product.price() == null || product.price().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Product price must not be null or non Postive");
        if (product.stock() == null || product.stock() <= 0)
            throw new IllegalArgumentException("Product price Stock not be null or non Postive");
    }

    // Es posible que a futuro cambie la validacion para update y create pero hoy es lo mismo
    private void validateInput(UpdateProductRequestDTO product) {
        if (product == null) throw new IllegalArgumentException("Product must not be null");
        if (product.name() == null || product.name().isBlank())
            throw new IllegalArgumentException("Product name must not be null or empty");
        if (product.description() == null || product.description().isBlank())
            throw new IllegalArgumentException("Product description must not be null or empty");
        if (product.price() == null || product.price().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Product price must not be null or non Postive");
        if (product.stock() == null || product.stock() <= 0)
            throw new IllegalArgumentException("Product Stock must not be null or non Postive");
    }

    private Product getProductOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }


    @Override
    public List<ProductResponseDTO> findAll(String name, BigDecimal minPrice, BigDecimal maxPrice, Boolean outOfStock) {
        boolean hasName = name != null && !name.isBlank();
        boolean hasMin = minPrice != null;
        boolean hasMax = maxPrice != null;

        if (outOfStock != null) {
            if (hasName || hasMin || hasMax) {
                throw new IllegalArgumentException("outOfStock cannot be combined with other filters");
            }

            if (outOfStock) {
                return repository.findOutOfStockProducts().stream()
                        .map(ProductResponseDTO::fromProduct)
                        .toList();
            }
            return repository.findInStockProducts().stream()
                    .map(ProductResponseDTO::fromProduct)
                    .toList();
        }

        if (hasName && hasMax && !hasMin) {
            return repository.searchProducts(name, maxPrice.doubleValue()).stream()
                    .map(ProductResponseDTO::fromProduct)
                    .toList();
        }

        if (hasName && !hasMin) {
            return repository.findByNameCaseInsensitive(name).stream()
                    .map(ProductResponseDTO::fromProduct)
                    .toList();
        }

        if (!hasName && hasMin && hasMax) {
            return repository.findByPriceRange(minPrice, maxPrice).stream()
                    .map(ProductResponseDTO::fromProduct)
                    .toList();
        }

        if (!hasName && !hasMin && !hasMax) {
            return repository.findAll().stream()
                    .map(ProductResponseDTO::fromProduct)
                    .toList();
        }

        throw new IllegalArgumentException("Unsupported combination of query params");
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        return ProductResponseDTO.fromProduct(getProductOrThrow(id));
    }

    @Override
    public ProductResponseDTO update(Long id, UpdateProductRequestDTO updated) {
        validateInput(updated);
        Product existing = getProductOrThrow(id);

        existing.setName(updated.name());
        existing.setDescription(updated.description());
        existing.setPrice(updated.price());
        existing.setStock(updated.stock());

        return ProductResponseDTO.fromProduct(repository.save(existing));
    }

    @Override
    public ProductResponseDTO updateStock(Long id, UpdateStockRequestDTO request) {
        if (request == null || request.newStock() == null || request.newStock() <= 0) {
            throw new IllegalArgumentException("Stock must be greater than zero");
        }

        int rowsUpdated = repository.updateProductStock(id, request.newStock());
        if (rowsUpdated == 0) {
            throw new ResourceNotFoundException("Product not found");
        }

        return ProductResponseDTO.fromProduct(getProductOrThrow(id));
    }

    @Override
    public void delete(Long id) {
        if(!repository.existsById(id)) throw new ResourceNotFoundException("Product not found");
        // TODO: validar constraint de ordenes existentes
        repository.deleteById(id);
    }

    @Override
    public ProductStatsResponseDTO findStats() {
        Double stockValue = repository.findTotalStockValue();
        BigDecimal stock = stockValue == null ? BigDecimal.ZERO : BigDecimal.valueOf(stockValue);

        BigDecimal averagePrice = repository.findAveragePrice();
        averagePrice = averagePrice == null ? BigDecimal.ZERO : averagePrice;

        return new ProductStatsResponseDTO(stock, averagePrice);
    }
}