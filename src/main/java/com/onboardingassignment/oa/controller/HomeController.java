package com.onboardingassignment.oa.controller;

import com.onboardingassignment.oa.model.User;
import com.onboardingassignment.oa.repository.product.ProductCrudRepository;
import com.onboardingassignment.oa.repository.user.UserCrudRepository;
import com.onboardingassignment.oa.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class HomeController {

    private final UserCrudRepository userCrudRepository;
    private final ProductCrudRepository productCrudRepository;
    private final PasswordEncoder passwordEncoder;

    private final ProductService productService;

    public HomeController(
            UserCrudRepository userCrudRepository,
            ProductCrudRepository productCrudRepository,
            PasswordEncoder passwordEncoder, ProductService productService) {
        this.userCrudRepository = userCrudRepository;
        this.productCrudRepository = productCrudRepository;
        this.passwordEncoder = passwordEncoder;
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
    public String registerNewUser(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String email) {

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setEmail(email);
        newUser.setRole("USER");

        userCrudRepository.save(newUser);

        return "Registered";
    }

    // --------------------------
    //   Admin: Get All Users
    // --------------------------

    @GetMapping("/users")
    @ResponseBody
    public Iterable<User> getAllUsers() {
        return userCrudRepository.findAll();
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