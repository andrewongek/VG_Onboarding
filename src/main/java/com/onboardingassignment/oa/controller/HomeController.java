package com.onboardingassignment.oa.controller;

import com.onboardingassignment.oa.model.CartItem;
import com.onboardingassignment.oa.model.User;
import com.onboardingassignment.oa.security.CustomUserDetails;
import com.onboardingassignment.oa.services.CartService;
import com.onboardingassignment.oa.services.ProductService;
import com.onboardingassignment.oa.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    private final UserService userService;
    private final ProductService productService;
    private final CartService cartService;

    public HomeController(UserService userService, ProductService productService, CartService cartService) {
        this.userService = userService;
        this.productService = productService;
        this.cartService = cartService;
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

    @PostMapping("/add_to_cart")
    public String addToCart(@AuthenticationPrincipal CustomUserDetails user,
                            @RequestParam String code,
                            RedirectAttributes redirectAttributes) {

        cartService.addCartItem(user.getId(), code);
        redirectAttributes.addFlashAttribute("success", "Item added to cart!");
        return "redirect:/home";
    }

    @GetMapping("my_cart")
    public String cartPage(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        List<CartItem> cartItems = cartService.getCartItemsList(user.getId());
        int grandTotal = cartItems.stream()
                .mapToInt(CartItem::getTotalPrice)
                .sum();
        model.addAttribute("isEmpty", cartItems.isEmpty());
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("grandTotal", grandTotal);
        return "cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long id) {
        cartService.deleteCartItem(id);
        return "redirect:/my_cart";
    }

    @PostMapping("/buy")
    public String buyProduct(@AuthenticationPrincipal CustomUserDetails user,
                             @RequestParam String code) {
        cartService.addCartItem(user.getId(), code);
        return "redirect:/my_cart";
    }

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