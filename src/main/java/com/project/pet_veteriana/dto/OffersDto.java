package com.project.pet_veteriana.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OffersDto {

    private Integer offerId;
    private String name;
    private String description;
    private String discountType;
    private BigDecimal discountValue;
    private Boolean isActive;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
