package zone01.com.lets_play;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

import zone01.com.lets_play.user.User;
import zone01.com.lets_play.user.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return null;
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.get().email())
                .password(user.get().password())  // Le mot de passe est déjà encodé
                .roles(user.get().role())  // Convertit les rôles en tableau
                .build();
    }
}
