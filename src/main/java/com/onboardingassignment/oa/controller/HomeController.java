package com.onboardingassignment.oa.controller;

import com.onboardingassignment.oa.services.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/"})
    public String homePage(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("productlist", productService.getProductList());
        return "home";
    }


}