package com.api.productengine.controller;

import com.api.productengine.dto.ProductDTO;
import com.api.productengine.mapper.ProductMapper;
import com.api.productengine.model.Product;
import com.api.productengine.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductDTO create(@RequestBody ProductDTO dto) {
        Product product = ProductMapper.toEntity(dto);
        return ProductMapper.toDTO(productService.create(product));
    }

    @GetMapping
    public List<ProductDTO> getAll() {
        return productService.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable Long id) {
        return ProductMapper.toDTO(productService.findById(id));
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        Product product = ProductMapper.toEntity(dto);
        return ProductMapper.toDTO(productService.update(id, product));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}