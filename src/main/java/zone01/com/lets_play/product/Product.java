package zone01.com.lets_play.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

@Document(collection = "products")
public record Product(
    
    @Id String id,

    @NotBlank(message = "The product name must not be blank")
    @Size(min = 2, max = 100, message = "The name must be between 2 and 100 characters")
    String name,

    @NotBlank(message = "The description must not be blank")
    @Size(max = 500, message = "The description must not exceed 500 characters")
    String description,

    @NotNull(message = "The price must not be null")
    @Positive(message = "The price must be a positive number")
    Double price,

    String userId
) {

    public Product withId(String id2) {
        return new Product(id2, this.name, this.description, this.price, this.userId);
    }

    public Product withUserId(String userid) {
        return new Product(this.id, this.name, this.description, this.price, userid);
    }
}
