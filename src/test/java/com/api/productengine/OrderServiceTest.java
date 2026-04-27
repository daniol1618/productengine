package com.api.productengine;

import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.repository.OrderRepository;
import com.api.productengine.repository.ProductRepository;
import com.api.productengine.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

	@MockitoBean
	private OrderRepository orderRepository;

	@MockitoBean
	private ProductRepository productRepository;

	@Autowired
	private OrderService orderService;

	@Test
	public void testCreateOrderSuccess() {
		Product product = new Product("Monitor", "Full HD", BigDecimal.valueOf(200), 5);
		product.setId(1L);

		Order order = new Order(1L, 2);

		Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
		Mockito.when(productRepository.save(product)).thenReturn(product);
		Mockito.when(orderRepository.save(order)).thenReturn(order);

		Order savedOrder = orderService.create(order);

		assertNotNull(savedOrder);
		assertEquals(3, product.getStock());
	}

	@Test
	public void testCreateOrderWithoutProductId() {
		Order order = new Order(null, 1);

		assertThrows(IllegalArgumentException.class, () -> orderService.create(order));
	}

	@Test
	public void testCreateOrderWithInvalidQuantity() {
		Order order = new Order(1L, 0);

		assertThrows(IllegalArgumentException.class, () -> orderService.create(order));
	}

	@Test
	public void testCreateOrderWithProductNotFound() {
		Order order = new Order(99L, 1);

		Mockito.when(productRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> orderService.create(order));
	}

	@Test
	public void testCreateOrderWithInsufficientStock() {
		Product product = new Product("Tablet", "Android tablet", BigDecimal.valueOf(300), 2);
		product.setId(1L);

		Order order = new Order(1L, 5);

		Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

		assertThrows(RuntimeException.class, () -> orderService.create(order));
	}

	@Test
	public void testFindOrderByIdNotFound() {
		Mockito.when(orderRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> orderService.findById(99L));
	}
}
