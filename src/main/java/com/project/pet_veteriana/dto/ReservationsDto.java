package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class ReservationsDto {

    private Integer reservationId;
    private Integer userId;
    private Integer serviceId;
    private Integer availabilityId;
    private LocalDateTime date;
    private String status;
    private LocalDateTime createdAt;

    public ReservationsDto() {
    }

    public ReservationsDto(Integer reservationId, Integer userId, Integer serviceId, Integer availabilityId,
                           LocalDateTime date, String status, LocalDateTime createdAt) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.serviceId = serviceId;
        this.availabilityId = availabilityId;
        this.date = date;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(Integer availabilityId) {
        this.availabilityId = availabilityId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
