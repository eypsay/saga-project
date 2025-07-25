package com.sayilircoder.saga_order_service.controller;

import com.sayilircoder.saga_order_service.dto.ApiResponse;
import com.sayilircoder.saga_order_service.dto.CreateOrderRequest;
import com.sayilircoder.saga_order_service.dto.OrderCreatedEvent;
import com.sayilircoder.saga_order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderCreatedEvent>> createOrder(@Valid @RequestBody CreateOrderRequest orderRequest) {
        try {

            OrderCreatedEvent event = orderService.createOrder(orderRequest);
            return ResponseEntity.ok(
                    new ApiResponse<>("SUCCESS", "Order created successfully", event));

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(
                            new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }
}
