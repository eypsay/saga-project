package com.sayilircoder.saga_orchestrator_service.dto;

import java.util.List;

public record InventoryCheckFailedEvent(
        Long orderId,
        Long customerId,
        List<InventoryCheckedEvent.CheckOrderItem> items,
        String errorMessage) {

}
