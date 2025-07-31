package com.sayilircoder.saga_payment_service.service;

import com.sayilircoder.saga_payment_service.dto.StartPaymentEvent;

public interface PaymentService {
    void processPayment(StartPaymentEvent event);
    void reversePayment(Long orderId);
}
