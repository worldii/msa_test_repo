package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.model.OrderEntity;
import com.example.orderservice.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


    @Transactional
    public OrderResponse createOrder(final OrderRequest request, final String userId) {
        OrderEntity order = mapToOrder(request, userId);
        OrderEntity savedOrder = orderRepository.save(order);

        return OrderResponse.from(savedOrder);
    }

    private OrderEntity mapToOrder(final OrderRequest request, final String userId) {
        return OrderEntity.builder()
            .productId(request.getProductId())
            .totalPrice(request.getQty() * request.getUnitPrice())
            .unitPrice(request.getUnitPrice())
            .qty(request.getQty())
            .userId(userId)
            .orderId(UUID.randomUUID().toString())
            .build();
    }

    public OrderResponse getOrderByOrderId(final String orderId) {
        final OrderEntity orderEntity = orderRepository.findByOrderId(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        return OrderResponse.from(orderEntity);
    }

    public List<OrderResponse> getOrdersByUserId(final String userId) {
        final List<OrderEntity> orders = orderRepository.findByUserId(userId);
        return orders.stream()
            .map(OrderResponse::from)
            .collect(Collectors.toList());
    }


}
