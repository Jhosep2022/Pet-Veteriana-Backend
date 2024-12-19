package com.project.pet_veteriana.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPointsDto {

    private Integer userPointsId;
    private BigDecimal points;
    private String description;
    private LocalDateTime createdAt;
    private Integer userId;
}
