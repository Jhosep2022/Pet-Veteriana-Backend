package com.project.pet_veteriana.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicesDto {

    private Integer serviceId;
    private String serviceName;
    private BigDecimal price;
    private Integer duration;
    private String description;
    private LocalDateTime createdAt;
    private Boolean status;
    private Integer providerId;
    private Integer imageId;
}
