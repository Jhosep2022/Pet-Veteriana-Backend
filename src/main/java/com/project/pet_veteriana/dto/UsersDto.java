package com.project.pet_veteriana.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDto {

    private Integer userId;
    private String name;
    private String email;
    private String phoneNumber;
    private Integer type;
    private Integer location;
    private String preferredLanguage;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private Boolean status;
    private Integer rolId;
    private Integer imageId;
    private List<Integer> petIds;
}
