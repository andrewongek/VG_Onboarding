package com.onboardingassignment.oa.repository;

import com.onboardingassignment.oa.model.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Product findByCode(String code);

    Product getById(int id);
}
