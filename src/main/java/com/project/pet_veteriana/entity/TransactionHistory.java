package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
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

    @Column(name = "status", nullable = false, length = 20) // Cambio a String con longitud máxima de 20 caracteres
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)  // Permite eliminación en cascada
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Services service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Products product;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "amount_per_unit", nullable = false)
    private Double amountPerUnit;

    @Column(name = "detail", length = 500)
    private String detail;

    public TransactionHistory() {
    }

    public TransactionHistory(Integer transactionHistoryId, Double totalAmount, String status, LocalDateTime createdAt, Users user, Services service, Products product, Double quantity, Double amountPerUnit, String detail) {
        this.transactionHistoryId = transactionHistoryId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.user = user;
        this.service = service;
        this.product = product;
        this.quantity = quantity;
        this.amountPerUnit = amountPerUnit;
        this.detail = detail;
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getAmountPerUnit() {
        return amountPerUnit;
    }

    public void setAmountPerUnit(Double amountPerUnit) {
        this.amountPerUnit = amountPerUnit;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
