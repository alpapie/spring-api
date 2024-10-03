package zone01.com.lets_play.product;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
public interface  ProductRepository  extends MongoRepository<Product,String>{
    List<Product> findByUserId(String userId);
}
