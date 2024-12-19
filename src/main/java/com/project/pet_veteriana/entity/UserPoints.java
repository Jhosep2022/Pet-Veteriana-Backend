package com.project.pet_veteriana.entity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "User_points")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPoints implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_points_id", nullable = false)
    private Integer userPointsId;

    @Column(name = "points", nullable = false, precision = 3, scale = 2)
    private BigDecimal points;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relación con Users
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
}
