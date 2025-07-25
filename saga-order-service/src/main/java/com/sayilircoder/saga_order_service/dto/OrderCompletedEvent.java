package com.sayilircoder.saga_order_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderCompletedEvent(
        @NotNull(message = "Order ID is required")
        @Positive(message = "Order ID imust be poistive")
        Long orderId
) {
}
