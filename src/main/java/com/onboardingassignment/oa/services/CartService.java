package com.onboardingassignment.oa.services;

import com.onboardingassignment.oa.model.Cart;
import com.onboardingassignment.oa.model.CartItem;
import com.onboardingassignment.oa.repository.CartItemRepository;
import com.onboardingassignment.oa.repository.CartRepository;
import com.onboardingassignment.oa.repository.ProductRepository;
import com.onboardingassignment.oa.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(UserRepository userRepository, ProductRepository productRepository, CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItem> getCartItemsList(int userId) {
        var cart = cartRepository.findByUserId(userId);
        System.out.println(cart);
        if (cart == null) {
            return List.of(); // return empty list if no cart exists
        }
        return cartItemRepository.findByCartId(cart.getId());
    }

    @Transactional
    public void addCartItem(int userId, String code) {
        // Check for the User
        var user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        var product = productRepository.findByCode(code);
        if (product == null) {
            throw new IllegalArgumentException("Product not found: " + code);
        }

        if (product.getStock() <= 0) {
            throw new IllegalStateException("Product out of stock: " + code);
        }

        var cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        var existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            cartItemRepository.save(existingItem);
        } else {
            var cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItemRepository.save(cartItem);
        }
    }
}