package com.sayilircoder.saga_inventory_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayilircoder.saga_inventory_service.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryReverseConsumer {
    private static final Logger logger = LoggerFactory.getLogger(InventoryReverseConsumer.class);
    private static final String INVENTORY_REVERSE_TOPIC = "inventory-reverse";
    private final InventoryService inventoryService;

    public InventoryReverseConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = INVENTORY_REVERSE_TOPIC, groupId = "inventory-service-group")
    public void consume(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Long orderId = mapper.readValue(message, Long.class);
            logger.info("Received inventory reverse request for order ID: {}", orderId);
            inventoryService.reverseInventory(orderId);
            logger.info("Inventory successfully reversed for order: {}", orderId);
        } catch (Exception e) {
            logger.error("Failed to reverse inventory: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
