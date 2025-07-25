package com.sayilircoder.saga_order_service.service.impl;

import com.sayilircoder.saga_order_service.dto.CreateOrderRequest;
import com.sayilircoder.saga_order_service.dto.OrderCreatedEvent;
import com.sayilircoder.saga_order_service.entity.Order;
import com.sayilircoder.saga_order_service.entity.OrderStatus;
import com.sayilircoder.saga_order_service.repository.OrderRepository;
import com.sayilircoder.saga_order_service.service.OrderService;
import com.sayilircoder.saga_order_service.utility.mapper.OrderItemMapper;
import jakarta.transaction.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String ORDER_CREATED_TOPIC = "order-created";

    public OrderServiceImpl(OrderRepository orderRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional
    public OrderCreatedEvent createOrder(CreateOrderRequest orderRequest) {
        Order order = new Order();
        order.setCustomerId(orderRequest.customerId());
        order.setTotalAmount(orderRequest.totalAmount());
        order.setStatus(OrderStatus.CREATED);
        order.setOrderItems(OrderItemMapper.toEntityList(orderRequest.orderItems()));
        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getCustomerId(),
                savedOrder.getTotalAmount(),
                OrderItemMapper.toDtoList(savedOrder.getOrderItems())
        );
        kafkaTemplate.send(ORDER_CREATED_TOPIC, event);
        return event;
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found by ID:" + orderId));
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

    }

    @Override
    @Transactional
    public void completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found by ID:" + orderId));
        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }
}
