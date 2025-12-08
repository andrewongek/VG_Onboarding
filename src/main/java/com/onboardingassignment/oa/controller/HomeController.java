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
    public String homePage(Model model,
                           Authentication authentication,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           @RequestParam(defaultValue = "id") String sortBy,
                           @RequestParam(defaultValue = "asc") String direction) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("productlist", productService.getPaginatedProductList(page, size, sortBy, direction, null));
        return "home";
    }

    @GetMapping("/product/{id}")
    public String viewProduct(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails user, Model model) {
        var isFavourited = userFavouritesService.isFavourited(user.getId(), id);
        System.out.println(isFavourited);
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("favourited", isFavourited);
        return "item-info";
    }

    @PostMapping("/product/{id}")
    public ResponseEntity<Void> toggleFavourite(@PathVariable int id, @RequestParam int favourite, @AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        userFavouritesService.toggleFavourites(user.getId(), id, favourite);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my_favourites")
    public String viewFavourites(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        var userFavouritesProductIds = userFavouritesService.getProductIdsByUserId(user.getId());

        model.addAttribute("productlist", productService.getProductListFromIds(userFavouritesProductIds));
        return "favourites";
    }

    @GetMapping("/search")
    public String searchProducts(Model model,
                                 Authentication authentication,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sortBy,
                                 @RequestParam(defaultValue = "asc") String direction,
                                 @RequestParam String keyword) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("productlist", productService.getPaginatedProductList(page, size, sortBy, direction, keyword));
        return "home";
    }

}