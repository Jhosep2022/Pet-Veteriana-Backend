package com.project.pet_veteriana.dto;

import lombok.*;
import java.time.LocalDateTime;

public class ProvidersDto {

    private Integer providerId;
    private Integer userId;
    private Double rating;
    private LocalDateTime createdAt;
    private Integer status;
    private Integer imageId;  // Se añade el campo para la imagen

    public ProvidersDto() {
    }

    public ProvidersDto(Integer providerId, Integer userId, Double rating, LocalDateTime createdAt, Integer status, Integer imageId) {
        this.providerId = providerId;
        this.userId = userId;
        this.rating = rating;
        this.createdAt = createdAt;
        this.status = status;
        this.imageId = imageId;  // Inicializamos el campo imageId
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
}
