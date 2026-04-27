package com.api.productengine.service;

import com.api.productengine.model.Producto;
import com.api.productengine.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    // Inyección de dependencias por constructor
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    @Transactional
    public Producto guardar(Producto producto) {
        // Validación: El producto debe tener un saldo positivo
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            throw new RuntimeException("No se puede registrar un producto con saldo menor o igual a cero.");
        }

        // Validación: El stock no puede ser negativo al crear
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new RuntimeException("El stock inicial no puede ser negativo.");
        }

        return productoRepository.save(producto);
    }
}