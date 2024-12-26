package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class ProvidersDto {

    private Integer providerId;
    private Integer userId;
    private Double rating;
    private LocalDateTime createdAt;
    private Boolean status;


    public ProvidersDto() {
    }

    public ProvidersDto(Integer providerId, Integer userId, Double rating, LocalDateTime createdAt, Boolean status) {
        this.providerId = providerId;
        this.userId = userId;
        this.rating = rating;
        this.createdAt = createdAt;
        this.status = status;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
