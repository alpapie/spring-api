package zone01.com.lets_play.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {
   @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public void registerUser(User user)  {
        userRepository.save(user);
    }

    public User updateUser(String id, User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName(); 

        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
            boolean isAdmin = roles.stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        System.out.println(user.role());
        if (user.email().equals(currentUserEmail) || isAdmin) {

            user = new User(user.id(), updatedUser.name(), user.email(), updatedUser.password(), updatedUser.role());
            return userRepository.save(user);
        }
        throw new SecurityException("You can only update your own account.");

    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public User getUserByEmail(String email) {
        Optional<User> user=userRepository.findByEmail(email);
        return user.isPresent()?user.get():null;
    }

    public  Optional<User> getAuth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

       return userRepository.findByEmail(currentUserEmail);
    }
}
