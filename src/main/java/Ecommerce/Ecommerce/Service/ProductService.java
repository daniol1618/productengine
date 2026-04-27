package Ecommerce.Ecommerce.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import Ecommerce.Ecommerce.Model.Product;
import Ecommerce.Ecommerce.repository.ProductRepository;


@Service
public class ProductService {
    
    private final ProductRepository repository;

    public ProductService(ProductRepository repository){
        this.repository = repository;
    }

    public List<Product> findAll(){
        return repository.findAll();
    }

    public Product findById(Long id){
        return repository.findById(id).orElseThrow(()->new RuntimeException("Error 404"));
    }

    public Product create(Product product){
        return repository.save(product);
    }

    public void delete(Long id){
        Product existing = findById(id);
        repository.delete(existing);
        
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = repository.findById(id).orElseThrow(()->new RuntimeException("Error 404"));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());
        
        return repository.save(existingProduct);
    }
    

}
