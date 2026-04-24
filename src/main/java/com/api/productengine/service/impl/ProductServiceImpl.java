package com.api.productengine.service.impl;

import com.api.productengine.dto.product.CreateProductRequestDTO;
import com.api.productengine.dto.product.ProductResponseDTO;
import com.api.productengine.dto.product.UpdateProductRequestDTO;
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
    public List<ProductResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(ProductResponseDTO::fromProduct)
                .toList();
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

        existing = repository.save(existing);
        return ProductResponseDTO.fromProduct(existing);
    }

    @Override
    public void delete(Long id) {
        if(!repository.existsById(id)) throw new ResourceNotFoundException("Product not found");
        // TODO: validar constraint de ordenes existentes
        repository.deleteById(id);
    }
}
