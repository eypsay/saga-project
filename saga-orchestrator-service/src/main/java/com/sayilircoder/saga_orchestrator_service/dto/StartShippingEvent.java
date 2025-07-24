package com.sayilircoder.saga_orchestrator_service.dto;

public record StartShippingEvent(
        Long orderId,
        Long customerId,
        String shippingAddress
) {
}
