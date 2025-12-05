package com.onboardingassignment.oa.repository;

import com.onboardingassignment.oa.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderOrderId(long orderId);
}
