package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Specialty")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Specialty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialty_id", nullable = false)
    private Integer specialtyId;

    @Column(name = "name_specialty", nullable = false, length = 50)
    private String nameSpecialty;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relación con SprecialityProviders
    @OneToMany(mappedBy = "specialty", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SprecialityProviders> specialtyProviders;
}