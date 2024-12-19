package com.project.pet_veteriana.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProvidersDto {

    private Integer providerId;
    private Integer userId;
    private BigDecimal rating;
    private LocalDateTime createdAt;
    private Integer status;
    private List<Integer> productIds;
}
