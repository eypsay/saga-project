package com.sayilircoder.saga_inventory_service.service.impl;

import com.sayilircoder.saga_inventory_service.dto.StartInventoryCheckEvent;
import com.sayilircoder.saga_inventory_service.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {
    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);
    private static final String INVENTORY_CHECKED_TOPIC = "inventory-checked";
    private static final String INVENTORY_CHECK_FAILED_TOPIC = "inventory-failed";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InventoryServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void checkInventory(StartInventoryCheckEvent event) {

    }

    @Override
    public void reverseInventory(long orderId) {

    }
}
