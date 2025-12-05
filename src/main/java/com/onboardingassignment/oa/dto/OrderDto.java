package com.onboardingassignment.oa.dto;

import com.onboardingassignment.oa.model.OrderStatus;

import java.util.List;

public record OrderDto(Long orderId, List<OrderItemDto> items, int totalPrice, OrderStatus orderStatus, long cTime) {
}
