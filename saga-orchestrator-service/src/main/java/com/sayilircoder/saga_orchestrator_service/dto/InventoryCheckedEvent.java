package com.sayilircoder.saga_orchestrator_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record InventoryCheckedEvent(
        @NotNull(message = "Order Id is required")
        @Positive(message = "Order Id must be positive")
        Long orderId,
        @NotNull(message = "Customer ID is required")
        @Positive(message = "Customer ID must be positive")
        Long customerId,
        @NotNull(message = "Item List is required")
        List<CheckOrderItem> items,
        @NotNull(message = "Check ID is required")
        String checkId
) {
    public record CheckOrderItem(
            @NotNull(message = "Product ID is required")
            @Positive(message = "Product ID must be positive")
            Long productId,
            @NotNull(message = "Quantity ID is required")
            @Positive(message = "Quantity ID must be positive")
            Integer quantity,
            @NotNull(message = "Available flag is required")
            Boolean available
    ) {
    }

}
