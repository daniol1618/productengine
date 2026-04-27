package com.api.productengine;

import com.api.productengine.model.Order;
import com.api.productengine.model.Product;
import com.api.productengine.service.OrderService;
import com.api.productengine.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductengineApplicationTests {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Test
	void contextLoads() {
	}


	@Test
	public void testCreateOrderSuccess() {
		Product product = productService.create(
				new Product("Mouse", "Mouse gamer", BigDecimal.valueOf(50), 5)
		);

		Order order = new Order(product.getId(), "Compra mouse", 2);
		Order savedOrder = orderService.create(order);

		assertNotNull(savedOrder.getId());
		Product updatedProduct = productService.findById(product.getId());
		assertEquals(3, updatedProduct.getStock());
	}

	@Test
	public void testCreateOrderWithInvalidQuantity() {
		Product product = productService.create(
				new Product("Teclado", "Teclado mecánico", BigDecimal.valueOf(100), 5)
		);

		Order order = new Order(product.getId(), "Compra inválida", 0);

		assertThrows(IllegalArgumentException.class, () -> {
			orderService.create(order);
		});
	}

}