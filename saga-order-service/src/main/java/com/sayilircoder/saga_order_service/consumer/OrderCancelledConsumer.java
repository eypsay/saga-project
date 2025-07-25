package com.sayilircoder.saga_order_service.consumer;

import com.sayilircoder.saga_order_service.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCancelledConsumer {
    private static final Logger logger = LoggerFactory.getLogger(OrderCancelledConsumer.class);
    private final OrderService orderService;

    public OrderCancelledConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    private static final String ORDER_CANCELLED_TOPIC = "order-canceled";

    @KafkaListener(topics = ORDER_CANCELLED_TOPIC, groupId = "order-service-group")
    public void consume(Long orderId) {
        try {
            logger.info("Received order cancellation request for Order ID: {}", orderId);
            orderService.cancelOrder(orderId);
            logger.info("Order successfully cancelled: {}", orderId);
        } catch (Exception e) {
            logger.error("Failed to cancel order: {}", e.getMessage());
            throw new RuntimeException("Error processing order cancelled event", e);
        }

    }

}
