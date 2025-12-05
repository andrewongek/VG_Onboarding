package com.onboardingassignment.oa.controller;

import com.onboardingassignment.oa.model.CartItem;
import com.onboardingassignment.oa.security.CustomUserDetails;
import com.onboardingassignment.oa.services.CartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/buy")
    public String buyProduct(@AuthenticationPrincipal CustomUserDetails user,
                             @RequestParam String code) {
        cartService.addCartItem(user.getId(), code);
        return "redirect:/my_cart";
    }

    @PostMapping("/add_to_cart")
    public String addToCart(@AuthenticationPrincipal CustomUserDetails user,
                            @RequestParam String code,
                            RedirectAttributes redirectAttributes) {

        cartService.addCartItem(user.getId(), code);
        redirectAttributes.addFlashAttribute("success", "Item added to cart!");
        return "redirect:/";
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

    @DeleteMapping("/cart-item")
    public String removeFromCart(@RequestParam Long id) {
        cartService.deleteCartItem(id);
        return "redirect:/my_cart";
    }

    //    @PostMapping("/cart/checkout")
//    public String checkoutCart(@RequestParam Long id) {
//        cartService.
//    }
}
