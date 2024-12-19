package com.project.pet_veteriana.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageS3Dto {

    private Integer imageId;
    private String fileName;
    private String fileType;
    private String size;
    private LocalDateTime uploadDate;
    private Integer providerId;
}
