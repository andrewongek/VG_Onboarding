package com.onboardingassignment.oa.controller;

import com.onboardingassignment.oa.security.CustomUserDetails;
import com.onboardingassignment.oa.services.ProductService;
import com.onboardingassignment.oa.services.UserFavouritesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private final ProductService productService;
    private final UserFavouritesService userFavouritesService;

    public HomeController(ProductService productService, UserFavouritesService userFavouritesService) {
        this.productService = productService;
        this.userFavouritesService = userFavouritesService;
    }

    @GetMapping("/")
    public String homePage(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("productlist", productService.getProductList());
        return "home";
    }

    @GetMapping("/product/{id}")
    public String viewProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("favourited", true);
        return "item-info";
    }

    @PostMapping("/product/{id}")
    public ResponseEntity<Void> toggleFavourite(@RequestParam int favourite, @AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
//        return ResponseEntity.ok(userFavouritesService.toggleFavourites(user.getId(), );)
    }
}