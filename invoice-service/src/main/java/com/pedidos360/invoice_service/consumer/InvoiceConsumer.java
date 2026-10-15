package com.pedidos360.invoice_service.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class InvoiceConsumer {

    @RabbitListener(queues = "${app.queue.invoice}")
    public void processInvoice(String orderDetails, Channel channel, Message message) throws IOException {
        try {
            System.out.println("🧾 Orden recibida en Facturación. Generando boleta para: " + orderDetails);
            
            // Confirmación exitosa (ACK)
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            System.err.println("❌ Error al generar la factura.");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
}