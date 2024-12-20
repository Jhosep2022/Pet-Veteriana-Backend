package com.project.pet_veteriana.entity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transaction_history")
public class TransactionHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transations_history_id", nullable = false)
    private Integer transactionHistoryId;

    @Column(name = "total_amount", nullable = false, precision = 10)
    private Double totalAmount;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relación con Users
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    // Relación con Services
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Services service;

    // Relación con Products
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    public TransactionHistory() {
    }

    public TransactionHistory(Integer transactionHistoryId, Double totalAmount, Boolean status, LocalDateTime createdAt, Users user, Services service, Products product) {
        this.transactionHistoryId = transactionHistoryId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.user = user;
        this.service = service;
        this.product = product;
    }

    public Integer getTransactionHistoryId() {
        return transactionHistoryId;
    }

    public void setTransactionHistoryId(Integer transactionHistoryId) {
        this.transactionHistoryId = transactionHistoryId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
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

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }
}