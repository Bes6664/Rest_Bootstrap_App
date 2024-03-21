package com.bootstrap.Bootstrap_App.controller;

import com.bootstrap.Bootstrap_App.model.Role;
import com.bootstrap.Bootstrap_App.model.User;
import com.bootstrap.Bootstrap_App.repositories.RoleRepository;
import com.bootstrap.Bootstrap_App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api")
public class RestAdminController {
    private UserService service;
    private final PasswordEncoder encoder;
    private final RoleRepository repository;

    @Autowired
    public RestAdminController(UserService service, RoleRepository repository, PasswordEncoder encoder) {
        this.service = service;
        this.repository = repository;
        this.encoder = encoder;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(service.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        List<Role> resultList = new ArrayList<>();
        List<Role> roleList = user.getRoles();
        Optional<Role> roleUser = repository.findById(1);
        Optional<Role> roleAdmin = repository.findById(2);
        if (roleList.isEmpty() || roleList==null) {
            resultList.add(roleUser.get());
        } else if (roleList.get(0).getRole()=="ROLE_ADMIN") {
            resultList.add(roleUser.get());
        }
        if (roleList.get(0).getId() == 1) {
            resultList.add(roleUser.get());
        } else if (roleList.get(0).getId() == 2) {
            resultList.add(roleAdmin.get());
        }
        user.setRoles(resultList);
        user.setPassword(encoder.encode(user.getPassword()));
        service.saveUser(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable("id") int id) {
        return new ResponseEntity<>(service.getUserById(id),HttpStatus.OK);
    }

    @PostMapping("user/{id}/edit")
    public ResponseEntity<User> userEdit(@PathVariable("id") int id, @RequestBody User user) {
        if(user.getPassword().length()!=60) {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        List<Role> resultList = new ArrayList<>();
        List<Role> roleList = user.getRoles();
        Optional<Role> roleUser = repository.findById(1);
        Optional<Role> roleAdmin = repository.findById(2);
        if (roleList.isEmpty() || roleList==null) {
            resultList.add(roleUser.get());
        } else if (roleList.get(0).getRole()=="ROLE_ADMIN") {
            resultList.add(roleUser.get());
        }
        if (roleList.get(0).getId() == 1) {
            resultList.add(roleUser.get());
        } else if (roleList.get(0).getId() == 2) {
            resultList.add(roleAdmin.get());
        }
        user.setRoles(resultList);
        service.updateUser(id,user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("user/{id}/delete")
    public ResponseEntity<HttpStatus> userDelete(@PathVariable("id") int id) {
        service.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
