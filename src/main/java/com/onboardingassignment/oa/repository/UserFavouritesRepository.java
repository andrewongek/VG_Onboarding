package com.onboardingassignment.oa.repository;

import com.onboardingassignment.oa.model.Product;
import com.onboardingassignment.oa.model.User;
import com.onboardingassignment.oa.model.UserFavourites;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFavouritesRepository extends JpaRepository<UserFavourites, Long> {
    // Check if product is favourited
    Optional<UserFavourites> findByUserIdAndProductId(int userId, long productId);
    // Get all the products that the user favourited
    List<UserFavourites> findByUserId(int userId);
    //
}