package com.pedidos360.rabbitmq_admin.dto;

import jakarta.validation.constraints.NotBlank;

public class QueueRequestDTO {
    @NotBlank(message = "El nombre de la cola no puede estar vacío")
    private String name;

    // Getters y Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}