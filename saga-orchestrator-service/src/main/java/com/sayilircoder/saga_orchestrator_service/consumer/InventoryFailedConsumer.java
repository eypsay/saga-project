package com.sayilircoder.saga_orchestrator_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayilircoder.saga_orchestrator_service.dto.InventoryCheckFailedEvent;
import com.sayilircoder.saga_orchestrator_service.service.OrchestratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryFailedConsumer {
    private static final Logger logger = LoggerFactory.getLogger(InventoryFailedConsumer.class);
    private final OrchestratorService orchestratorService;

    public InventoryFailedConsumer(OrchestratorService orchestratorService) {
        this.orchestratorService = orchestratorService;
    }

    @KafkaListener(topics = "inventory-failed", groupId = "orchestrator-service-group")
    public void consume(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InventoryCheckFailedEvent event = mapper.readValue(message, InventoryCheckFailedEvent.class);
            logger.info("Received inventory failed event for order: {}", event.orderId());
            orchestratorService.handleInventoryFailed(event.orderId());
            ;
        } catch (Exception e) {
            logger.error("Error processing inventory failed event: {}", e.getMessage());
            throw new RuntimeException("Error processing inventory failed event", e);
        }

    }
}
