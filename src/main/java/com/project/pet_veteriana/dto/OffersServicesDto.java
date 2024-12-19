package com.project.pet_veteriana.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OffersServicesDto {

    private Integer offersServicesId;
    private Integer serviceId;
    private Integer offerId;
}
