package com.sayilircoder.saga_payment_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayilircoder.saga_payment_service.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentReverseConsumer {
    private static final Logger logger = LoggerFactory.getLogger(PaymentReverseConsumer.class);
    private static final String PAYMENT_REVERSE_TOPIC = "payment-reverse";
    private final PaymentService paymentService;

    public PaymentReverseConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = PAYMENT_REVERSE_TOPIC, groupId = "payment-service-group")
    public void handlePaymentReverse(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Long orderId = mapper.readValue(message, Long.class);
            logger.info("Received payment reverse event for order: {}", orderId);
            paymentService.reversePayment(orderId);
            logger.info("Successfully processed payment reverse for order: {}", orderId);
        } catch (Exception e) {
            logger.error("Error process,ng payment reverse for order: {}", message, e);
            throw new RuntimeException("Error processing Payment: {}", e);
        }
    }
}
