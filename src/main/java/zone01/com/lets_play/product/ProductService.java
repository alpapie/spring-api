package zone01.com.lets_play.product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product product) {
        ;
        return productRepository.save(product.withId(id));
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByUserId(String userId) {
        return productRepository.findByUserId(userId);
    }
}