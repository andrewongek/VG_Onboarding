package com.onboardingassignment.oa.controller;

import com.onboardingassignment.oa.model.User;
import com.onboardingassignment.oa.services.ProductService;
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
public class HomeController {

    private final UserService userService;
    private final ProductService productService;

    public HomeController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    // --------------------------
    //   Authentication Pages
    // --------------------------

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

    @GetMapping({"/", "/home"})
    public String homePage(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("productlist", productService.getProductList().products());
        return "home";
    }

    @PostMapping("/home/buy/{code}")
    public String buyProduct(@PathVariable String code) {
        productService.buy(code);
        return "redirect:/home";
    }

//    @PostMapping("/home/add-to-cart/{code}")
//    public String addToCart(@PathVariable String code) {
//        productService.addToCart(code);
//        return "redirect:/home";
//    }

    @GetMapping("/admin")
    public String adminPage(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "admin";
    }

    // --------------------------
    //   Registration
    // --------------------------

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> registerNewUser(@RequestParam String username,
                                                  @RequestParam String password,
                                                  @RequestParam String email) {

        if (username == null || username.isBlank() ||
                password == null || password.isBlank() ||
                email == null || email.isBlank()) {

            return ResponseEntity.badRequest().body("All fields are required.");
        }

        userService.registerUser(username, password, email, "USER");
        return ResponseEntity.ok("Registered");
    }

    // --------------------------
    //   Admin: Get All Users
    // --------------------------

    @GetMapping("/users")
    @ResponseBody
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
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