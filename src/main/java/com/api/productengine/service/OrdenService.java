package com.api.productengine.service;

import com.api.productengine.dto.OrdenDTO;
import com.api.productengine.dto.ProductoDTO;
import com.api.productengine.model.Orden;
import com.api.productengine.model.Producto;
import com.api.productengine.repository.OrdenRepository;
import com.api.productengine.repository.ProductoRepository;

import jakarta.transaction.Transactional;

import com.api.productengine.repository.ProductoRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final ProductoRepository productoRepository;

    public OrdenService(OrdenRepository ordenRepository, ProductoRepository productoRepository) {
        this.ordenRepository = ordenRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public Orden create(OrdenDTO request) {

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (request.getCantidad() == null || request.getCantidad() <= 0) {
            throw new RuntimeException("La orden no puede tener 0");
        }

        if(producto.getStock() < request.getCantidad()) {
            throw new RuntimeException("No hay suficiente stock para completar la orden");
        }

        BigDecimal total = producto.getPrecio().multiply(BigDecimal.valueOf(request.getCantidad()));
        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El total de la orden debe ser mayor a cero");
        }

        producto.setStock(producto.getStock() - request.getCantidad());
        productoRepository.save(producto);

  
        Orden orden = new Orden(
            UUID.randomUUID().toString(),
            producto,       
            request.getCantidad(),
            total,
            LocalDate.now()
        );
        return ordenRepository.save(orden);
    }

    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }       

}
