package com.project.pet_veteriana.entity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Table(name = "Products")
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "price", nullable = false, precision = 10)
    private Double price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "created_at", nullable = false)
    private Integer createdAt;

    @Column(name = "status", nullable = false)
    private Boolean status;

    // Relación con Providers
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Providers provider;

    // Relación con Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Relación con ImageS3
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", referencedColumnName = "image_id", nullable = false)
    private ImageS3 image;

    public Products() {
    }

    public Products(Integer productId, Double price, Integer stock, Integer createdAt, Boolean status, Providers provider, Category category, ImageS3 image) {
        this.productId = productId;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
        this.status = status;
        this.provider = provider;
        this.category = category;
        this.image = image;
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

    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ImageS3 getImage() {
        return image;
    }

    public void setImage(ImageS3 image) {
        this.image = image;
    }
}