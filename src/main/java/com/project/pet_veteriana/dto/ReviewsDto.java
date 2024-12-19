package com.project.pet_veteriana.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewsDto {

    private Integer reviewsId;
    private BigDecimal rating;
    private String comment;
    private LocalDateTime createdAt;
    private Integer userId;
    private Integer providerId;
}
