package com.bootstrap.Bootstrap_App.controller;

import com.bootstrap.Bootstrap_App.model.Role;
import com.bootstrap.Bootstrap_App.model.User;
import com.bootstrap.Bootstrap_App.repositories.RoleRepository;
import com.bootstrap.Bootstrap_App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService service;
    private final PasswordEncoder encoder;
    private final RoleRepository repository;
    @Autowired
    public UserController(UserService service, PasswordEncoder encoder, RoleRepository repository) {
        this.service = service;
        this.encoder = encoder;
        this.repository = repository;
    }

    @GetMapping("/panel")
    public String test(Model model,@AuthenticationPrincipal User currentUser) {
        model.addAttribute("user",currentUser);
        return "user";
    }

}
