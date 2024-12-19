package com.project.pet_veteriana.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecialtyDto {

    private Integer specialtyId;
    private String nameSpecialty;
    private LocalDateTime createdAt;
}
