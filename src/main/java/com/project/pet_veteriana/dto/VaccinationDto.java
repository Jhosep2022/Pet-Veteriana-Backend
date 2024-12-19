package com.project.pet_veteriana.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationDto {

    private Integer vaccinationId;
    private String name;
    private LocalDateTime createdAt;
}
