package com.api.productengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.productengine.model.Orden;
import com.api.productengine.model.Product;
import com.api.productengine.repository.ProductRepository;
import com.api.productengine.repository.RepositorioOrden;
import com.api.productengine.service.ServicioOrden;

@ExtendWith(MockitoExtension.class)

public class ServicioOrdenTest {

        @Mock
        private RepositorioOrden orderRepository;

        @Mock
        private ProductRepository productRepository;

        @InjectMocks
        private ServicioOrden orderService;

        @Test
        void shouldCreateOrderWhenStockIsAvailable() {
                Product product = new Product(
                                "Notebook",
                                "Lenovo",
                                BigDecimal.valueOf(1500),
                                2);

                when(productRepository.findById(1L))
                                .thenReturn(Optional.of(product));

                when(orderRepository.save(any(Orden.class)))
                                .thenAnswer(invocation -> invocation.getArgument(0));

                Orden order = orderService.createOrder(1L);

                assertNotNull(order);
                assertEquals(BigDecimal.valueOf(1500), order.getTotal());
                assertEquals(1, product.getStock());

                verify(orderRepository).save(any(Orden.class));
                verify(productRepository).save(product);
        }

        @Test
        void shouldFailWhenProductHasNo() {
                Product product = new Product(
                                "Notebook",
                                "Lenovo",
                                BigDecimal.valueOf(1500),
                                0);

                when(productRepository.findById(1L))
                                .thenReturn(Optional.of(product));

                RuntimeException ex = assertThrows(
                                RuntimeException.class,
                                () -> orderService.createOrder(1L));

                assertEquals("Product has no stock", ex.getMessage());
        }

        @Test
        void shouldFailWhenProductDoesNotExist() {
                when(productRepository.findById(1L))
                                .thenReturn(Optional.empty());

                RuntimeException ex = assertThrows(
                                RuntimeException.class,
                                () -> orderService.createOrder(1L));

                assertEquals("Product not found", ex.getMessage());
        }
}
