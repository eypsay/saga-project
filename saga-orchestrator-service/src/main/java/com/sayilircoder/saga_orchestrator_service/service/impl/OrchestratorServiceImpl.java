package com.sayilircoder.saga_orchestrator_service.service.impl;

import com.sayilircoder.saga_orchestrator_service.dto.InventoryCheckedEvent;
import com.sayilircoder.saga_orchestrator_service.dto.StartShippingEvent;
import com.sayilircoder.saga_orchestrator_service.service.OrchestratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service

public class OrchestratorServiceImpl implements OrchestratorService {
    public static final Logger logger = LoggerFactory.getLogger(OrchestratorServiceImpl.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrchestratorServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //ORDER
    private static final String ORDER_COMPLETED_TOPIC = "order-completed";
    private static final String ORDER_CANCELLED_TOPIC = "order-cancelled";

    // INVENTORY
    private static final String INVENTORY_CHECK_TOPIC = "inventory-check";
    private static final String INVENTORY_REVERSE_TOPIC = "inventory-reverse";

    // PAYMENT
    private static final String PAYMENT_START_TOPIC = "payment-start";
    private static final String PAYMENT_FAILED_TOPIC = "payment-failed";
    private static final String PAYMENT_REVERSE_TOPIC = "payment-reverse";


    //SHIPPING
    private static final String SHIPPING_START_TOPIC = "shipping-start";


    @Override
    public void handleInventoryChecked(InventoryCheckedEvent event) {
        logger.info("Inventory checked for order {}", event.orderId());
        StartShippingEvent shippingEvent = new StartShippingEvent(event.orderId(), event.customerId(), null);
        kafkaTemplate.send(SHIPPING_START_TOPIC, shippingEvent);
        logger.info("Shipping start event sent for order: {}", event.orderId());
    }

    @Override
    public void handleInventoryFailed(Long orderId) {
        logger.info("Inventory checked for order {}", orderId);

        kafkaTemplate.send(ORDER_CANCELLED_TOPIC, orderId);
        logger.info("Order cancelled event sent for order: {}", orderId);

        kafkaTemplate.send(PAYMENT_REVERSE_TOPIC, orderId);
        logger.info("Payment cancel event sent for order: {}", orderId);
    }
}
