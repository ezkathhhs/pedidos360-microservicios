package com.pedidos360.orders_service.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app.exchange.orders}")
    private String exchangeName;

    // Declaramos el bean del Exchange central
    @Bean
    public Exchange ordersExchange() {
        return new DirectExchange(exchangeName, true, false);
    }
}