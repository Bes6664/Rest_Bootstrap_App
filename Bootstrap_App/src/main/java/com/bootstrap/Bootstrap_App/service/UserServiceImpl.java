package com.bootstrap.Bootstrap_App.service;


import com.bootstrap.Bootstrap_App.model.User;
import com.bootstrap.Bootstrap_App.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(int id) {
        return repository.findById(id).get();
    }

    @Override
    public void saveUser(User user) {
        repository.save(user);
    }

    @Override
    public void updateUser(int id, User updatedUser) {
        repository.save(updatedUser);
    }

    @Override
    public void deleteUser(int id) {
        repository.deleteById(id);
    }
}
