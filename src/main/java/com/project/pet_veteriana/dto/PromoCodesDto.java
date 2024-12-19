package com.project.pet_veteriana.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromoCodesDto {

    private Integer promoId;
    private String code;
    private String description;
    private String discountType;
    private BigDecimal discountValue;
    private Integer maxUses;
    private Integer currentUses;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
    private Integer providerId;
    private LocalDateTime createdAt;
}
