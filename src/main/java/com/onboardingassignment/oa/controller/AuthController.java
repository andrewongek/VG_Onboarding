package com.onboardingassignment.oa.controller;

import com.onboardingassignment.oa.dto.NewUserDto;
import com.onboardingassignment.oa.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model) {

        if (error != null) {
            model.addAttribute("error", true);
        }

        if (logout != null) {
            model.addAttribute("logout", true);
        }

        return "login";
    }

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


    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> registerNewUser(@RequestBody NewUserDto newUserDto) {

        if (newUserDto.username() == null || newUserDto.username().isBlank() ||
                newUserDto.password() == null || newUserDto.password().isBlank() ||
                newUserDto.email() == null || newUserDto.email().isBlank()) {

            return ResponseEntity.badRequest().body("All fields are required.");
        }

        userService.registerUser(newUserDto.username(), newUserDto.password(), newUserDto.email(), "USER");
        return ResponseEntity.ok("Registered");
    }
}
