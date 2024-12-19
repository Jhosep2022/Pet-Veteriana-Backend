package com.project.pet_veteriana.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprecialityProvidersDto {

    private Integer idSpPro;
    private Integer specialtyId;
    private Integer providerId;
}
