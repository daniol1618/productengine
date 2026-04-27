package com.api.productengine.mapper;

import com.api.productengine.dto.ProductDTO;
import com.api.productengine.model.Product;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getStock()

        );
    }

    public static Product toEntity(ProductDTO dto) {
        Product product = new Product();
                product.SetId(dto.getId());
                product.setName(dto.getName());
                product.setPrice(dto.getPrice());
                product.setDescription(dto.getDescription());
                product.setStock(dto.getStock());
                return product;

    }


}
