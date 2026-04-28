package com.api.productengine.service;

import com.api.productengine.model.Product;
import com.api.productengine.repository.ProductRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Service marks this class as a Spring-managed service bean.
 * Spring will automatically detect and register it in the application context.
 */
@Service
public class ProductScheduler {

    private final ProductRepository productRepository;

    public ProductScheduler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * @Scheduled enables the method to run periodically based on the configuration.
     * The 'cron' attribute defines the execution schedule using a cron expression.
     * '${app.scheduling.product-save-cron}' is a placeholder that retrieves the
     * actual cron expression from the application.yaml file.
     */
    @Scheduled(cron = "${app.scheduling.product-save-cron}")
    public void saveNewProduct() {
        Product product = new Product();
        product.setName("My Batch Product " + LocalDateTime.now());
        product.setDescription("Automatically generated product at " + LocalDateTime.now());
        product.setPrice(new BigDecimal("19.99"));
        product.setStock(100);

        System.out.println("The product to be saved is" + product.getName());
        productRepository.save(product);
        System.out.println("Saved new product: " + product.getName());
    }
}
