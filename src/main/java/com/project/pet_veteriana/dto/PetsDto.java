package com.project.pet_veteriana.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetsDto {

    private Integer petId;
    private String petName;
    private String petBreed;
    private String petAge;
    private LocalDateTime createdAt;
    private Double weight;
    private Double height;
    private String gender;
    private String allergies;
    private String behaviorNotes;
    private Integer userId;
    private Integer imageId;
}
