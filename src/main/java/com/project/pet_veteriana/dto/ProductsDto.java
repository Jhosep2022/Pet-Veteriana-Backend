package com.project.pet_veteriana.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductsDto {

    private Integer productId;
    private BigDecimal price;
    private Integer stock;
    private Integer createdAt;
    private Boolean status;
    private Integer providerId;
    private Integer categoryId;
}
