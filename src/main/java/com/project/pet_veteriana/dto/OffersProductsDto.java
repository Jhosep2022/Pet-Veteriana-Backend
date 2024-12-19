package com.project.pet_veteriana.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OffersProductsDto {

    private Integer offersProductsId;
    private Integer offerId;
    private Integer productId;
}
