package com.project.pet_veteriana.dto;

import lombok.*;

public class ProductsDto {

    private Integer productId;
    private Double price;
    private Integer stock;
    private Integer createdAt;
    private Boolean status;
    private Integer providerId;
    private Integer categoryId;

    public ProductsDto() {
    }

    public ProductsDto(Integer productId, Double price, Integer stock, Integer createdAt, Boolean status, Integer providerId, Integer categoryId) {
        this.productId = productId;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
        this.status = status;
        this.providerId = providerId;
        this.categoryId = categoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
