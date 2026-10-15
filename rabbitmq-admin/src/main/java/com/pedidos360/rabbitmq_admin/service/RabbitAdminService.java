package com.pedidos360.rabbitmq_admin.service;

import org.springframework.amqp.core.*;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class RabbitAdminService {

    private final AmqpAdmin amqpAdmin;

    public RabbitAdminService(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    // Método actualizado para incluir DLQ automáticamente
    public String createQueue(String queueName) {
        // 1. Crear el Exchange y la Cola para la DLQ (Mensajes muertos/fallidos)
        String dlqExchange = queueName + ".dlx";
        String dlqName = queueName + ".dlq";
        String dlqRoutingKey = queueName + ".routing.dlq";

        amqpAdmin.declareExchange(new DirectExchange(dlqExchange));
        amqpAdmin.declareQueue(new Queue(dlqName, true));
        amqpAdmin.declareBinding(BindingBuilder.bind(new Queue(dlqName)).to(new DirectExchange(dlqExchange)).with(dlqRoutingKey));

        // 2. Crear la cola principal y vincularla a su DLQ
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", dlqExchange);
        args.put("x-dead-letter-routing-key", dlqRoutingKey);
        
        Queue mainQueue = new Queue(queueName, true, false, false, args);
        amqpAdmin.declareQueue(mainQueue);

        return "Cola principal '" + queueName + "' y su respectiva DLQ '" + dlqName + "' creadas exitosamente.";
    }

    public String deleteQueue(String queueName) {
        amqpAdmin.deleteQueue(queueName);
        // Opcional: Eliminar la DLQ también si se desea
        amqpAdmin.deleteQueue(queueName + ".dlq");
        return "Cola '" + queueName + "' y su DLQ eliminadas exitosamente.";
    }

    public String createExchange(String exchangeName, String type) {
        Exchange exchange = "topic".equalsIgnoreCase(type) ? 
            new TopicExchange(exchangeName, true, false) : 
            new DirectExchange(exchangeName, true, false);
        amqpAdmin.declareExchange(exchange);
        return "Exchange '" + exchangeName + "' (" + type + ") creado exitosamente.";
    }

    // Nuevo método para Bindings
    public String createBinding(String queueName, String exchangeName, String routingKey) {
        Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, routingKey, null);
        amqpAdmin.declareBinding(binding);
        return "Binding creado exitosamente entre la cola '" + queueName + "' y el exchange '" + exchangeName + "'.";
    }
}