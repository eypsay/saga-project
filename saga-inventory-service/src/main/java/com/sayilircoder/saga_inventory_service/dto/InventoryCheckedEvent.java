package com.sayilircoder.saga_inventory_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record InventoryCheckedEvent(
        @NotNull(message = "Order ID is required")
        @Positive(message = "Order ID must be positive")
        Long orderId,
        @NotNull(message = "Customer ID is required")
        @Positive(message = "Customer ID must be positive")
        Long customerId,
        @NotNull(message = "Items list is required")
        List<CheckedOrderItem> items,
        @NotNull(message = "Check ID is required")
        String checkId
) {
    public record OrderItem(
            @NotNull(message = "Product ID is required")
            @Positive(message = "Product ID must be positive")
            Long productId,

            @NotNull(message = "Quantity is required")
            @Positive(message = "Quantity must be positive")
            Integer quantity,

            @NotNull(message = "Available flag is required")
            Boolean available) {

    }
}
