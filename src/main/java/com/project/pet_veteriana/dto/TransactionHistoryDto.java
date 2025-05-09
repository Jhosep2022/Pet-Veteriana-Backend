package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class TransactionHistoryDto {

    private Integer transactionHistoryId;
    private Double totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private Integer userId;
    private Integer serviceId;
    private Integer productId;
    private Double quantity;
    private Double amountPerUnit;
    private String detail;

    public TransactionHistoryDto() {
    }

    public TransactionHistoryDto(Integer transactionHistoryId, Double totalAmount, String status, LocalDateTime createdAt, Integer userId, Integer serviceId, Integer productId, Double quantity, Double amountPerUnit, String detail) {
        this.transactionHistoryId = transactionHistoryId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.userId = userId;
        this.serviceId = serviceId;
        this.productId = productId;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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
