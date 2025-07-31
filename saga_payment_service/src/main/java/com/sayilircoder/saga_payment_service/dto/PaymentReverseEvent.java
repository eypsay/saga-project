package com.sayilircoder.saga_payment_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentReverseEvent(
        @NotNull(message = "Order ID cannot be null")
        @Positive(message = "Order ID must be positive")
        Long orderId
) {
}
