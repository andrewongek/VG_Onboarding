package com.onboardingassignment.oa.controller;

import com.onboardingassignment.oa.entities.User;
import com.onboardingassignment.oa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html
    }

    @GetMapping({"/", "/home"})
    public String homePage() {
        return "home"; // login.html
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public @ResponseBody String registerNewUser(@RequestParam String username
            , @RequestParam String password) {
        User n = new User();
        n.setUsername(username);
        n.setPassword(passwordEncoder.encode(password));
        n.setRole("USER");
        userRepository.save(n);
        return "Registered";
    }

    @GetMapping("/users")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
