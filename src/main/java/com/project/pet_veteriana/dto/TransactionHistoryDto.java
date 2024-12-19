package com.project.pet_veteriana.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionHistoryDto {

    private Integer transactionHistoryId;
    private BigDecimal totalAmount;
    private Boolean status;
    private LocalDateTime createdAt;
    private Integer userId;
    private Integer serviceId;
    private Integer productId;
}
