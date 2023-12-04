package com.example.orderservice.dto;

import com.example.orderservice.model.OrderEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private Date createdAt;
    private String orderId;

    public static OrderResponse from(final OrderEntity savedOrder) {
        return OrderResponse.builder()
            .productId(savedOrder.getProductId())
            .qty(savedOrder.getQty())
            .unitPrice(savedOrder.getUnitPrice())
            .totalPrice(savedOrder.getTotalPrice())
            .createdAt(savedOrder.getCreatedAt())
            .orderId(savedOrder.getOrderId())
            .build();
    }
}
