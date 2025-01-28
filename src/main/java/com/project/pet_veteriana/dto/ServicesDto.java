package com.project.pet_veteriana.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ServicesDto {

    private Integer serviceId;
    private String serviceName;
    private Double price;
    private Integer duration;
    private String description;
    private LocalDateTime createdAt;
    private Boolean status;
    private Integer providerId;
    private Integer imageId;
    private String imageUrl; // Nuevo campo para el enlace de la imagen

    public ServicesDto() {
    }

    public ServicesDto(Integer serviceId, String serviceName, Double price, Integer duration, String description, LocalDateTime createdAt, Boolean status, Integer providerId, Integer imageId, String imageUrl) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.price = price;
        this.duration = duration;
        this.description = description;
        this.createdAt = createdAt;
        this.status = status;
        this.providerId = providerId;
        this.imageId = imageId;
        this.imageUrl = imageUrl; // Asignar el enlace de la imagen
    }

    // Getters y Setters
    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
