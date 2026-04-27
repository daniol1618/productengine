package com.api.productengine;

import com.api.productengine.model.Product;
import com.api.productengine.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.Repository;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class ProductengineApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductengineApplication.class, args);
		System.out.println("Hi David!!");
	}

}