package zone01.com.lets_play.user;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> 
{

    // Find all users with a specific role
    @Query("{ 'role': ?0 }")
    List<User> findUsersByRole(String role);

    // Find users where email contains a certain string
    @Query("{ 'email': ?0 }")
    Optional<User> findByEmail(String pattern);
}