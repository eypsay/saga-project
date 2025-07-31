package com.sayilircoder.saga_payment_service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayilircoder.saga_payment_service.dto.StartPaymentEvent;
import com.sayilircoder.saga_payment_service.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StartPaymentConsumer {
    private final static Logger logger = LoggerFactory.getLogger(StartPaymentConsumer.class);
    private final static String PAYMENT_START_TOPIC="payment-start";
    private final PaymentService paymentService;

    public StartPaymentConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @KafkaListener(topics = PAYMENT_START_TOPIC,groupId = "payment-service-group")
    public void consume(String message){

        try {
            ObjectMapper mapper= new ObjectMapper();
            StartPaymentEvent event= mapper.readValue(message,StartPaymentEvent.class);
            logger.info("Received payment start event: {}", event);
            paymentService.processPayment(event);
            logger.info("Payment process started for order: {}", event.orderId());
        } catch (Exception e) {
            logger.error("Error processing payment start event: {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }


}
