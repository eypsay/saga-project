package com.sayilircoder.saga_inventory_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayilircoder.saga_inventory_service.dto.StartInventoryCheckEvent;
import com.sayilircoder.saga_inventory_service.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StartInventoryCheckConsumer {
    private static final Logger logger = LoggerFactory.getLogger(StartInventoryCheckConsumer.class);

    private static final String INVENTORY_CHECK_TOPIC = "inventory-check";
    private final InventoryService inventoryService;

    public StartInventoryCheckConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = INVENTORY_CHECK_TOPIC, groupId = "inventory-service-group")
    public void consume(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            StartInventoryCheckEvent event = mapper.readValue(message, StartInventoryCheckEvent.class);
            logger.info("Received inventory check event: {}", event);
            inventoryService.checkInventory(event);
            logger.info("Inventory check started for order: {}", event.orderId());
        } catch (Exception e) {
            logger.error("Error processing inventory check event: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
