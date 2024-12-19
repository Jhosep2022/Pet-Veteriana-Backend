package com.project.pet_veteriana.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettingsDto {

    private Integer settingsId;
    private String key;
    private String value;
    private LocalDateTime createdAt;
    private Integer rolId;
}
