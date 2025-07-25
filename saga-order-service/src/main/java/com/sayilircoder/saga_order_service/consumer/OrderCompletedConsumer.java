package com.sayilircoder.saga_order_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayilircoder.saga_order_service.dto.OrderCompletedEvent;
import com.sayilircoder.saga_order_service.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCompletedConsumer {
    private static final Logger logger = LoggerFactory.getLogger(OrderCompletedConsumer.class);
    private static final String ORDER_COMPLETED_TOPIC = "order-completed";

    private final OrderService orderService;

    public OrderCompletedConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = ORDER_COMPLETED_TOPIC, groupId = "order-service-group")
    public void consume(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            OrderCompletedEvent event = mapper.readValue(message, OrderCompletedEvent.class);
            logger.info("Received order completion request for order ID: {}", event.orderId());
            orderService.completeOrder(event.orderId());
            logger.info("Order successfully completed: {}", event.orderId());
        } catch (Exception e) {
            logger.error("Failed to complete order: {}", e.getMessage());
            throw new RuntimeException("Error processing order completed event", e);
        }
    }
}
