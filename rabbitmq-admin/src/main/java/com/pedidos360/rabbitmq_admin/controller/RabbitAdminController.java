package com.pedidos360.rabbitmq_admin.controller;

import com.pedidos360.rabbitmq_admin.dto.ExchangeRequestDTO;
import com.pedidos360.rabbitmq_admin.dto.QueueRequestDTO;
import com.pedidos360.rabbitmq_admin.service.RabbitAdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pedidos360.rabbitmq_admin.dto.BindingRequestDTO;

@RestController
@RequestMapping("/api/admin/rabbitmq")
public class RabbitAdminController {

    private final RabbitAdminService adminService;

    public RabbitAdminController(RabbitAdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/queues")
    public ResponseEntity<String> createQueue(@Valid @RequestBody QueueRequestDTO request) {
        String response = adminService.createQueue(request.getName());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/queues/{name}")
    public ResponseEntity<String> deleteQueue(@PathVariable String name) {
        String response = adminService.deleteQueue(name);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/exchanges")
    public ResponseEntity<String> createExchange(@Valid @RequestBody ExchangeRequestDTO request) {
        String response = adminService.createExchange(request.getName(), request.getType());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bindings")
    public ResponseEntity<String> createBinding(@Valid @RequestBody BindingRequestDTO request) {
        String response = adminService.createBinding(request.getQueueName(), request.getExchangeName(), request.getRoutingKey());
        return ResponseEntity.ok(response);
    }
}