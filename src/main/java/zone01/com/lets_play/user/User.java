package zone01.com.lets_play.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Document(collection = "users")
public record User(
    @Id String id,

    @NotBlank(message = "Le nom ne doit pas être vide")
    @Size(min = 2, max = 50, message = "Le nom doit comporter entre 2 et 50 caractères")
    @Field
    String name,

    @Email(message = "Email invalide")
    @NotBlank(message = "L'email ne doit pas être vide")
    @Indexed(unique = true)
    String email,

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit comporter au moins 6 caractères")
    String password,

    @NotBlank(message = "Le rôle est obligatoire")
    String role
) {

    // Simulating a "setter" by returning a new record with updated password
    public User withPassword(String newPassword) {
        return new User(this.id, this.name, this.email, newPassword, this.role);
    }

    // Simulating a "setter" by returning a new record with updated ID
    public User withId(String newId) {
        return new User(newId, this.name, this.email, this.password, this.role);
    }

    public static User fromUser(User user) {
        return new User(
            user.id(),
            user.name(),
            user.email(),
            null,
            user.role()
        );
    }
}
