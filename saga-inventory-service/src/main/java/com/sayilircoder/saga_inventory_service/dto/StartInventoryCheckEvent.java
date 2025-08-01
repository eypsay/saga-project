package com.sayilircoder.saga_inventory_service.dto;

import java.util.List;

public record StartInventoryCheckEvent(
        Long orderId,
        Long customerId,
        List<OrderItem> items
) {
}
