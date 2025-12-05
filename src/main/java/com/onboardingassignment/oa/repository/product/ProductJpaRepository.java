package com.onboardingassignment.oa.repository.product;

import com.onboardingassignment.oa.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Integer> {
}
