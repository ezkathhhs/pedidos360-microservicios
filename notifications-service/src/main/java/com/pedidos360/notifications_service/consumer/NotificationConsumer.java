package com.pedidos360.notifications_service.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class NotificationConsumer {

    // Lee el nombre de la cola desde el application.yml
    @RabbitListener(queues = "${app.queue.notificaciones}")
    public void receiveMessage(String payload, Channel channel, Message message) throws IOException {
        try {
            System.out.println("📩 Evento recibido. Procesando notificación: " + payload);
            
            // --- NUEVO: Simulación de fallo ---
            if (payload.toLowerCase().contains("error")) {
                throw new RuntimeException("Fallo simulado porque el mensaje contiene la palabra 'error'");
            }
            
            System.out.println("✅ Simulación: Correo enviado exitosamente.");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            
        } catch (Exception e) {
            System.err.println("❌ Error al procesar la notificación: " + e.getMessage());
            // El false final es requeue=false, lo que envía el mensaje directo a la DLQ
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
}