package com.onboardingassignment.oa.repository.product;

import com.onboardingassignment.oa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Integer> {
    Product findByCode(String code);
}
