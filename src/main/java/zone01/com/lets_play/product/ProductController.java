package zone01.com.lets_play.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import zone01.com.lets_play.user.User;
import zone01.com.lets_play.user.UserService;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/api/admin/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        try {
            return productService.getProductById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
           return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/products")
    public ResponseEntity<String> createProduct(@Valid @RequestBody Product product) {
        try {
            Optional<User> user = userService.getAuth();
            if (user.isEmpty() ) {
                return ResponseEntity.status(401).body("Not authorize");
            }
            productService.createProduct(product.withUserId(user.get().id()));
            return ResponseEntity.ok("Create");
        } catch (Exception e) {
            System.out.println(e);
           return ResponseEntity.badRequest().body("Error in request");
        }
    }

    @PutMapping("/api/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @Valid @RequestBody Product product) {
        try {
            Optional<User> user = userService.getAuth();
            if (user.isEmpty() ) {
                ResponseEntity.badRequest().body("no content");
            }
            Optional<Product> productrr= productService.getProductById(id);
            if (productrr.isEmpty() || !productrr.get().userId().equals(user.get().id())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.ok(productService.updateProduct(id, product));
        } catch (Exception e) {
            System.out.println(e);
           return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/api/admin/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eror on delete procuc");
        }
    }

    @GetMapping("/api/user/product/{userId}")
    public ResponseEntity<List<Product>> getProductsByUserId(@PathVariable String userId) {
        Optional<User> user = userService.getAuth();
        System.out.println(user.get().role());
        if (user.get().id().equals(userId) || user.get().role().equals("ADMIN")) {
            return ResponseEntity.ok(productService.getProductsByUserId(userId));
        }
        return ResponseEntity.status(401).build();
    }
}