package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Providers")
public class Providers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id", nullable = false)
    private Integer providerId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private Users user;

    @Column(name = "rating", nullable = false, precision = 3)
    private Double rating;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)  // Relación con ImageS3
    @JoinColumn(name = "image_id", referencedColumnName = "image_id", nullable = false)
    private ImageS3 image;

    public Providers() {
    }

    public Providers(Integer providerId, Users user, Double rating, LocalDateTime createdAt, Integer status, ImageS3 image) {
        this.providerId = providerId;
        this.user = user;
        this.rating = rating;
        this.createdAt = createdAt;
        this.status = status;
        this.image = image;  // Relacionamos con la imagen
    }

    // Getters y Setters
    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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

    public ImageS3 getImage() {
        return image;
    }

    public void setImage(ImageS3 image) {
        this.image = image;
    }
}
