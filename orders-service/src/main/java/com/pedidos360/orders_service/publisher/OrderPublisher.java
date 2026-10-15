package com.pedidos360.orders_service.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.exchange.orders}")
    private String exchangeName;

    @Value("${app.routing-key.order-created}")
    private String routingKey;

    public OrderPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishOrderCreatedEvent(String orderDetails) {
        System.out.println("🚀 Publicando evento de nueva orden: " + orderDetails);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderDetails);
    }
}