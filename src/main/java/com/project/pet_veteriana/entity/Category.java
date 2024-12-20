package com.project.pet_veteriana.entity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Category")

public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "name_category", nullable = false, length = 150)
    private String nameCategory;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Category() {
    }

    public Category(Integer categoryId, String nameCategory, LocalDateTime createdAt) {
        this.categoryId = categoryId;
        this.nameCategory = nameCategory;
        this.createdAt = createdAt;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}