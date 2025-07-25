package com.sayilircoder.saga_order_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderCreatedEvent(
        @NotNull(message = "Order ID is required")
        @Positive(message = "Order ID imust be poistive")
        Long orderId,

        @NotNull(message = "Customer ID is required")
        @Positive(message = "Customer ID imust be poistive")
        Long Customer,
        @NotNull(message = "TotalAmount is required")
        @Positive(message = "TotalAmount imust be poistive")
        BigDecimal totalAmount,

        @NotEmpty(message = "Order items cant be empty")
        List<OrderItemDto> orderItems
) {
}
