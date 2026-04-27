package Ecommerce.Ecommerce;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Ecommerce.Ecommerce.Model.Order;
import Ecommerce.Ecommerce.Model.Product;
import Ecommerce.Ecommerce.Service.OrderService;
import Ecommerce.Ecommerce.Service.ProductService;

@RestController
@RequestMapping("/api")
public class CommerceController {
    private final ProductService productService;
    private final OrderService orderService;

    public CommerceController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public Order buy(@RequestBody Order request) {
        return orderService.creatOrder(request.getProduct().getId(), request.getQuantity());
    }


    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.findAll();
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.delete(id);
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        return productService.create(product); 
    }
}
