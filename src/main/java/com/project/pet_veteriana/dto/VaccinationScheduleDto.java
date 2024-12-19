package com.project.pet_veteriana.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationScheduleDto {

    private Integer vaccinationScheduleId;
    private LocalDateTime dateVaccination;
    private String status;
    private LocalDateTime createdAt;
    private Integer petId;
    private Integer vaccinationId;
}
