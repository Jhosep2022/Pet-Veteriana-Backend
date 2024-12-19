package com.project.pet_veteriana.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityLogsDto {

    private Integer activityLogsId;
    private Integer userId;
    private String action;
    private String description;
    private String ip;
    private LocalDateTime createdAt;
}
