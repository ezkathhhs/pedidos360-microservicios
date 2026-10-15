package com.pedidos360.orders_service.controller;

import com.pedidos360.orders_service.publisher.OrderPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderPublisher orderPublisher;

    public OrderController(OrderPublisher orderPublisher) {
        this.orderPublisher = orderPublisher;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody String orderDetails) {
        // Aquí iría la lógica de guardar en base de datos.
        
        // Disparamos el evento asíncrono
        orderPublisher.publishOrderCreatedEvent(orderDetails);
        
        return ResponseEntity.ok("Orden registrada y evento publicado exitosamente.");
    }
}