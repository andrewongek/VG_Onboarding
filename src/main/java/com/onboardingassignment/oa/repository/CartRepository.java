package com.onboardingassignment.oa.repository;

import com.onboardingassignment.oa.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(int userId);
}
