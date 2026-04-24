package com.api.productengine.service;

import com.api.productengine.dto.product.CreateProductRequestDTO;
import com.api.productengine.dto.product.ProductResponseDTO;
import com.api.productengine.dto.product.UpdateProductRequestDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO create(CreateProductRequestDTO product);

    List<ProductResponseDTO> findAll();

    ProductResponseDTO findById(Long id);

    ProductResponseDTO update(Long id, UpdateProductRequestDTO updated);

    void delete(Long id);
}