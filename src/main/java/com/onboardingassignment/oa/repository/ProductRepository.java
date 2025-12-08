package com.onboardingassignment.oa.repository;

import com.onboardingassignment.oa.model.Product;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Product findByCode(String code);

    Product getById(int id);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE %:keyword% OR LOWER(p.code) LIKE %:keyword%")
    List<Product> searchProductsByNameOrCode(@Param("keyword") String keyword);
}
