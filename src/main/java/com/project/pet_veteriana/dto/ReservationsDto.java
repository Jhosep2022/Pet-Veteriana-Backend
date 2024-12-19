package com.project.pet_veteriana.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationsDto {

    private Integer reservationId;
    private Integer userId;
    private Integer serviceId;
    private LocalDateTime date;
    private Boolean status;
    private LocalDateTime createdAt;
}
