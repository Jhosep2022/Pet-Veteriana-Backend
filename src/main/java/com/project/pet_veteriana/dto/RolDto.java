package com.project.pet_veteriana.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolDto {

    private Integer rolId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}
