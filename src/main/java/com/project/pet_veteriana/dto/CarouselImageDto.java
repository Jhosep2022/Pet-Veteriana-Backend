package com.project.pet_veteriana.dto;

public class CarouselImageDto {
    private Long id;
    private String url;

    // Constructor vacío
    public CarouselImageDto() {
    }

    // Constructor completo
    public CarouselImageDto(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
