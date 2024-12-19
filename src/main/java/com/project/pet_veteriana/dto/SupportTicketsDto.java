package com.project.pet_veteriana.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupportTicketsDto {

    private Integer supportTicketsId;
    private String subject;
    private String description;
    private Boolean status;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private Integer userId;
}
