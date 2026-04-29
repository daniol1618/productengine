package com.api.productengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @EnableScheduling triggers Spring's scheduled task execution capability,
 * allowing @Scheduled methods to be processed.
 */
@SpringBootApplication
@EnableScheduling
public class ProductengineApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductengineApplication.class, args);
		System.out.println("Application started successfully!");
	}

}
