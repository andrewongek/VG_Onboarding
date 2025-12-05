package com.onboardingassignment.oa.controller;

import com.onboardingassignment.oa.model.User;
import com.onboardingassignment.oa.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "admin";
    }

    @GetMapping("/users")
    @ResponseBody
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
