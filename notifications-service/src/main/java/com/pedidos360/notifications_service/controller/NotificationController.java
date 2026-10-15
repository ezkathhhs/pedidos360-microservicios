package com.pedidos360.notifications_service.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final RabbitTemplate rabbitTemplate;

    // Inyectamos el nombre de la cola desde el application.yml
    @Value("${app.queue.notificaciones}")
    private String queueName;

    public NotificationController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody String emailBody) {
        // Enviamos el mensaje a la cola de RabbitMQ
        rabbitTemplate.convertAndSend(queueName, emailBody);
        return ResponseEntity.ok("Mensaje enviado a la cola exitosamente: " + emailBody);
    }
}