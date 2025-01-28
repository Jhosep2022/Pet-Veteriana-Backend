package com.project.pet_veteriana.dto;

public class ProductsDto {

    private Integer productId;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Integer createdAt;
    private Boolean status;
    private Integer providerId;
    private Integer categoryId;
    private Integer imageId;
    private String imageUrl; // Nuevo campo para el enlace de la imagen

    public ProductsDto() {
    }

    public ProductsDto(Integer productId, String name, String description, Double price, Integer stock, Integer createdAt, Boolean status, Integer providerId, Integer categoryId, Integer imageId, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
        this.status = status;
        this.providerId = providerId;
        this.categoryId = categoryId;
        this.imageId = imageId;
        this.imageUrl = imageUrl; // Asignar el enlace de la imagen
    }

    // Getters y Setters
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
