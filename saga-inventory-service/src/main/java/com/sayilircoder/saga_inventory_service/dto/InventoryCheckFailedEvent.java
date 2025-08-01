package com.sayilircoder.saga_inventory_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record InventoryCheckFailedEvent(
        @NotNull(message = "Order ID is required")
        @Positive(message = "Order ID must be positive")
        Long orderId,
        @NotNull(message = "Customer ID is required")
        @Positive(message = "Customer ID must be positive")
        Long customerId,
        @NotNull(message = "Items list is required")
        List<CheckedOrderItem> items,
        @NotNull(message = "Error message is required")
        String errorMessage
) {
}
