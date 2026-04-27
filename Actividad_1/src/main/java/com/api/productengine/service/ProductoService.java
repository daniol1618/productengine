package com.api.productengine.service;

import com.api.productengine.dto.ProductoDTO;
import com.api.productengine.model.Producto;
import com.api.productengine.repository.ProductoRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository ProductoRepository;

    public ProductoService(ProductoRepository repository) {
        this.ProductoRepository = repository;
    }

    public Producto create(ProductoDTO request) {
        Producto producto = new Producto(
                request.getNombre(),
                request.getDescripcion(),
                request.getPrecio(),
                request.getStock()
        );
        return ProductoRepository.save(producto);
    }

    public List<Producto> findAll() {
        return ProductoRepository.findAll();
    }

    public Producto findById(Long id) {
        return ProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Producto update(Long id, Producto updated) {
        Producto existing = findById(id);
        existing.setNombre(updated.getNombre());
        existing.setDescripcion(updated.getDescripcion());
        existing.setPrecio(updated.getPrecio());
        existing.setStock(updated.getStock());

        return ProductoRepository.save(existing);
    }

    public void delete(Long id) {
        ProductoRepository.deleteById(id);
    }
}
