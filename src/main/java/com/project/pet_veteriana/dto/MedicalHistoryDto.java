package com.project.pet_veteriana.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalHistoryDto {

    private Integer medicalHistoryId;
    private LocalDateTime date;
    private String visitReason;
    private String symptoms;
    private LocalDateTime createdAt;
    private Integer petId;
}
