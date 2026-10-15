package com.pedidos360.rabbitmq_admin.dto;

import jakarta.validation.constraints.NotBlank;

public class ExchangeRequestDTO {
    @NotBlank(message = "El nombre del exchange no puede estar vacío")
    private String name;
    
    @NotBlank(message = "El tipo de exchange no puede estar vacío (ej: direct, topic)")
    private String type;

    // Getters y Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}