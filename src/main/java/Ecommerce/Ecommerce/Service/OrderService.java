package Ecommerce.Ecommerce.Service;

import java.math.BigDecimal;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import Ecommerce.Ecommerce.Model.Order;
import Ecommerce.Ecommerce.Model.Product;
import Ecommerce.Ecommerce.repository.OrderRepository;
import Ecommerce.Ecommerce.repository.ProductRepository;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository){
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public Order creatOrder(Long productId, Integer quantity){
        Product product = productRepository.findById(productId).
                                        orElseThrow(() -> new RuntimeException("Error 404"));

        if(product.getStock() < quantity){
            throw new RuntimeErrorException(null, "Error no hay en stock"+product.getStock());
        }

        BigDecimal total = product.getPrice().multiply(new BigDecimal(quantity));

        product.setStock(product.getStock()-quantity);
        productRepository.save(product);

        Order order = new Order(product, quantity, total);
        order.setProduct(product);

        return orderRepository.save(order);
    }
}