package pl.coderslab.finalproject.services.interfaces;

import pl.coderslab.finalproject.entities.User;

public interface UserService {
 
    User findByUserName(String username);
 
    void saveUser(User user);
} 