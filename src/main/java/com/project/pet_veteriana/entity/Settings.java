package com.project.pet_veteriana.entity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Settings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settings_id", nullable = false)
    private Integer settingsId;

    @Column(name = "key", nullable = false, length = 150)
    private String key;

    @Column(name = "value", nullable = false, length = 150)
    private String value;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relación con Rol
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;
}
