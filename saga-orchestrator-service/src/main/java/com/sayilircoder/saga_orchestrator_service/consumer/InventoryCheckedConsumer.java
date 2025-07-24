package com.sayilircoder.saga_orchestrator_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayilircoder.saga_orchestrator_service.dto.InventoryCheckedEvent;
import com.sayilircoder.saga_orchestrator_service.service.OrchestratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component

public class InventoryCheckedConsumer {
    private static final Logger logger = LoggerFactory.getLogger(InventoryCheckedConsumer.class);
    private final OrchestratorService orchestratorService;

    public InventoryCheckedConsumer(OrchestratorService orchestratorService) {
        this.orchestratorService = orchestratorService;
    }


    @KafkaListener(topics = "inventory-checked", groupId = "orchestrator-service-group")
    public void consume(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InventoryCheckedEvent event = mapper.readValue(message, InventoryCheckedEvent.class);
            logger.info("Received inventory checked event: {}", event);
            orchestratorService.handleInventoryChecked(event);
        } catch (Exception e) {
            logger.error("Error processing inventory checked event: {}", e.getMessage());
            throw new RuntimeException("Inventory checked event is not Processing", e);
        }
    }
}
