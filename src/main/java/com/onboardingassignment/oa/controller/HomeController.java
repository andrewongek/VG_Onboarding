package com.onboardingassignment.oa.controller;

import com.onboardingassignment.oa.entities.User;
import com.onboardingassignment.oa.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class HomeController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public HomeController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // --------------------------
    //   Authentication Pages
    // --------------------------

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping({"/", "/home"})
    public String homePage() {
        return "home";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    // --------------------------
    //   Registration
    // --------------------------

    @PostMapping("/register")
    @ResponseBody
    public String registerNewUser(@RequestParam String username,
                                  @RequestParam String password) {

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole("USER");

        userRepository.save(newUser);

        return "Registered";
    }

    // --------------------------
    //   Admin: Get All Users
    // --------------------------

    @GetMapping("/users")
    @ResponseBody
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    // --------------------------
    //   Post Login Redirect
    // --------------------------

    @GetMapping("/login-success")
    public void loginSuccessRedirect(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Authentication authentication) throws IOException {

        String role = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");

        String basePath = request.getContextPath();

        if ("ROLE_ADMIN".equals(role)) {
            response.sendRedirect(basePath + "/admin");
        } else {
            response.sendRedirect(basePath + "/");
        }
    }
}