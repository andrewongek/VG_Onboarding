package com.onboardingassignment.oa.controller;

import com.onboardingassignment.oa.dto.OrderDto;
import com.onboardingassignment.oa.security.CustomUserDetails;
import com.onboardingassignment.oa.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/api/order")
    public ResponseEntity<List<OrderDto>> getOrders(@AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(orderService.getOrdersList(user.getId()));
    }
}
