package com.onboardingassignment.oa.repository;

import com.onboardingassignment.oa.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(int userId);
}
