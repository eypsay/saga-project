package com.sayilircoder.saga_payment_service.service.impl;

import com.sayilircoder.saga_payment_service.dto.PaymentFailedEvent;
import com.sayilircoder.saga_payment_service.dto.PaymentProcessedEvent;
import com.sayilircoder.saga_payment_service.dto.StartPaymentEvent;
import com.sayilircoder.saga_payment_service.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final static Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);
    private final static String PAYMENT_PROCESSED_TOPIC = "payment-processed";
    private final static String PAYMENT_FAILED_TOPIC = "payment-failed";

    public PaymentServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void processPayment(StartPaymentEvent event) {

        try {
           /*
           //Simulate payment processing with 80% success rate
            boolean isPaymentSuccessful=Math.random()<0.8;
            if(isPaymentSuccessful){
                String transactionId = UUID.randomUUID().toString();
                PaymentProcessedEvent processedEvent = new PaymentProcessedEvent(
                        event.orderId(),
                        event.customerId(),
                        event.amount(),
                        transactionId,
                        event.orderItems()
                );
                kafkaTemplate.send(PAYMENT_PROCESSED_TOPIC, processedEvent);
                logger.info("Payment processed successfully for order: {}", event.orderId());
            }else {
                PaymentFailedEvent failedEvent = new PaymentFailedEvent(
                        event.orderId(),
                        event.customerId(),
                        event.amount(),
                        "payment processing failed ",
                        event.orderItems()

                );
                kafkaTemplate.send(PAYMENT_FAILED_TOPIC, failedEvent);
                logger.info("Payment processed failed for order: {}", event.orderId());
            }*/


            String transactionId = UUID.randomUUID().toString();
            PaymentProcessedEvent processedEvent = new PaymentProcessedEvent(
                    event.orderId(),
                    event.customerId(),
                    event.amount(),
                    transactionId,
                    event.orderItems()
            );
            kafkaTemplate.send(PAYMENT_PROCESSED_TOPIC, processedEvent);
            logger.info("Payment processed successfully for order: {}", event.orderId());
        } catch (Exception e) {
            logger.error("Error processing payment: {}", event.orderId());
            PaymentFailedEvent failedEvent = new PaymentFailedEvent(
                    event.orderId(),
                    event.customerId(),
                    event.amount(),
                    "Error processing payment" + e.getMessage(),
                    event.orderItems()

            );
            kafkaTemplate.send(PAYMENT_FAILED_TOPIC, failedEvent);
            throw e;
        }

    }

    @Override
    @Transactional
    public void reversePayment(Long orderId) {
        logger.info("Reversing payment for order: {}", orderId);
        // TODO: Implement actual payment reversal logic
        logger.info("Payment reversed successfully for order: {}", orderId);
    }
}
