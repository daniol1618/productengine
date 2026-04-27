package com.api.productengine.service;

import com.api.productengine.dto.OrdenDTO;
import com.api.productengine.exceptions.ExcepcionProductoNoEncontrado;
import com.api.productengine.exceptions.ExcepcionReglaNegocio;
import com.api.productengine.model.Orden;
import com.api.productengine.model.Producto;
import com.api.productengine.repository.OrdenRepository;
import com.api.productengine.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrdenService {

    private final ProductRepository repositorioProductos;
    private final OrdenRepository repositorioOrdenes;

    public OrdenService(ProductRepository repositorioProductos,
                        OrdenRepository repositorioOrdenes){
        this.repositorioOrdenes = repositorioOrdenes;
        this.repositorioProductos = repositorioProductos;
    }

    public Orden crearOrden(OrdenDTO ordenDTO){
        if (ordenDTO.getCantidad() == null || ordenDTO.getCantidad() != 1){
            throw new ExcepcionReglaNegocio("La orden debe contener 1 producto");
        }

        Producto producto = repositorioProductos.findById(ordenDTO.getId_producto())
                .orElseThrow(() -> new ExcepcionProductoNoEncontrado("El producto no existe"));

        if (producto.getStock() <= 0) {
            throw new ExcepcionReglaNegocio("No hay stock del producto seleccionado");
        }

        BigDecimal total = producto.getPrice();
        if (total.compareTo(BigDecimal.ZERO) <= 0){
            throw new ExcepcionReglaNegocio("El precio de la orden no puede ser negativo");
        }

        producto.setStock(producto.getStock() - 1);
        repositorioProductos.save(producto);

        Orden orden = new Orden();
        orden.setProducto(producto);
        orden.setPrecio(total);

        return repositorioOrdenes.save(orden);
    }
}
