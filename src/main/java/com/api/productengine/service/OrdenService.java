package com.api.productengine.service;

import com.api.productengine.model.Orden;
import com.api.productengine.model.Producto;
import com.api.productengine.repository.OrdenRepository;
import com.api.productengine.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final ProductoRepository productoRepository;

    // Inyección por constructor
    public OrdenService(OrdenRepository ordenRepository, ProductoRepository productoRepository) {
        this.ordenRepository = ordenRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public Orden crearOrden(Long productoId) {
        // 1. Validar existencia del producto
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("El producto no existe."));

        // 2. Validar stock
        if (producto.getStock() <= 0) {
            throw new RuntimeException("El producto no cuenta con stock.");
        }

        // 3. Validar saldo positivo (precio > 0)
        if (producto.getPrecio() <= 0) {
            throw new RuntimeException("La orden debe tener un saldo positivo.");
        }

        // 4. Procesar: Reducir stock y generar orden
        producto.setStock(producto.getStock() - 1);
        productoRepository.save(producto);

        Orden nuevaOrden = new Orden();
        nuevaOrden.setProducto(producto);
        nuevaOrden.setTotal(producto.getPrecio());
        nuevaOrden.setCodigoUnico(UUID.randomUUID().toString()); // Diferenciador único

        return ordenRepository.save(nuevaOrden);
    }
}