package zone01.com.lets_play.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        try {
            
            if (userService.getUserByEmail(user.email())!=null) {
                return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
            }
    
            String encodedPassword = passwordEncoder.encode(user.password());
            User newUser = new User(null, user.name(), user.email(), encodedPassword, user.role());
            userService.registerUser(newUser);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("error in request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/admin/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers().stream()
                .map(User::fromUser)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/api/admin/user/{id}")
    public ResponseEntity<String> delete(@PathVariable String id ){
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(String.format("user with id %s is successfully delete", id),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Delete eror review id ", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<String>  update( @Valid @RequestBody User user,@PathVariable String id){
        try {
            if (userService.getUserById(id).isEmpty()) {
                return ResponseEntity.status(404).body("No user fund");
            }
    
            String encodedPassword = passwordEncoder.encode(user.password());
            User newUser = new User(null, user.name(), user.email(), encodedPassword, user.role());
            userService.updateUser(id, newUser);    
           return  new ResponseEntity<>(String.format("user with id %s is successfully update", id),HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
        }
    }

}
