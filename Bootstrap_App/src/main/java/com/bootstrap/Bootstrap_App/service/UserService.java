package com.bootstrap.Bootstrap_App.service;

import com.bootstrap.Bootstrap_App.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;

public interface UserService extends UserDetailsService {
    public List<User> getAllUsers();

    public User getUserById(int id);

    public void saveUser(User user);

    public void updateUser(int id, User updatedUser);

    public void deleteUser(int id);
}
