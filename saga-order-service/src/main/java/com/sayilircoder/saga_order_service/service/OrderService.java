package com.sayilircoder.saga_order_service.service;

import com.sayilircoder.saga_order_service.dto.CreateOrderRequest;
import com.sayilircoder.saga_order_service.dto.OrderCreatedEvent;

public interface OrderService {
    OrderCreatedEvent createOrder(CreateOrderRequest orderRequest);
    void cancelOrder(Long orderId);
    void completeOrder(Long orderId);

}
