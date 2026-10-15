package com.pedidos360.invoice_service.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app.queue.invoice}")
    private String queueName;

    @Value("${app.exchange.orders}")
    private String exchangeName;

    @Value("${app.routing-key.order-created}")
    private String routingKey;

    @Bean
    public Queue invoiceQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public Exchange ordersExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    public Binding invoiceBinding(Queue invoiceQueue, Exchange ordersExchange) {
        return BindingBuilder.bind(invoiceQueue).to(ordersExchange).with(routingKey).noargs();
    }
}