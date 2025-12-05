package com.onboardingassignment.oa.services;

import com.onboardingassignment.oa.dto.OrderDto;
import com.onboardingassignment.oa.dto.OrderItemDto;
import com.onboardingassignment.oa.model.OrderItem;
import com.onboardingassignment.oa.repository.OrderItemRepository;
import com.onboardingassignment.oa.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderDto> getOrdersList(int userId) {
        var orders = orderRepository.findByUserId(userId);
        if (orders == null) {
            return List.of();
        }
        List<OrderDto> orderDtoList = new ArrayList<>();
        orders.forEach(order -> {
            var items = orderItemRepository.findByOrderOrderId(order.getOrderId());
            List<OrderItemDto> itemDtos = items.stream()
                    .map(item -> new OrderItemDto(
                            item.getProduct().getName(),
                            item.getProduct().getCode(),
                            item.getQuantity()
                    ))
                    .toList();
            orderDtoList.add(new OrderDto(
                    order.getOrderId(),
                    itemDtos,
                    order.getTotalPrice(),
                    order.getOrderStatus(),
                    order.getcTime()
            ));
        });
        return orderDtoList;
    }
}
