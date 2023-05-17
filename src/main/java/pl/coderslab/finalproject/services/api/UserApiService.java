package pl.coderslab.finalproject.services.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.entities.Role;
import pl.coderslab.finalproject.entities.User;
import pl.coderslab.finalproject.repository.RoleRepository;
import pl.coderslab.finalproject.repository.UserRepository;
import pl.coderslab.finalproject.services.interfaces.UserService;

import java.util.Arrays;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserApiService implements UserService {
 
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(User user) {
user.setPassword(passwordEncoder.encode(user.getPassword()));
user.setEnabled(1);
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>((Arrays.asList(userRole))));
        userRepository.save(user);
    }
}