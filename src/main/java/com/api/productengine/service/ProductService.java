package com.api.productengine.service;

import com.api.productengine.dto.product.*;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductResponseDTO create(CreateProductRequestDTO product);

    List<ProductResponseDTO> findAll(String name, BigDecimal minPrice, BigDecimal maxPrice, Boolean outOfStock);

    ProductResponseDTO findById(Long id);

    ProductResponseDTO update(Long id, UpdateProductRequestDTO updated);

    ProductResponseDTO updateStock(Long id, UpdateStockRequestDTO request);

    void delete(Long id);

    ProductStatsResponseDTO findStats();
}