package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reservations")
public class Reservations implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false)
    private Integer reservationId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "service_id", nullable = false)
    private Services service;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Reservations() {
    }

    public Reservations(Integer reservationId, Users user, Services service, LocalDateTime date, Boolean status, LocalDateTime createdAt) {
        this.reservationId = reservationId;
        this.user = user;
        this.service = service;
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}