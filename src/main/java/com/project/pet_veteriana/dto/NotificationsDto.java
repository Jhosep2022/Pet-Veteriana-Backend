package com.project.pet_veteriana.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationsDto {

    private Integer notificationId;
    private String message;
    private String notificationType;
    private Boolean isRead;
    private LocalDateTime createdAt;
    private Integer userId;
}
