package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Pets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id", nullable = false)
    private Integer petId;

    @Column(name = "pet_name", nullable = false, length = 150)
    private String petName;

    @Column(name = "pet_breed", nullable = false, length = 150)
    private String petBreed;

    @Column(name = "pet_age", nullable = false, length = 50)
    private String petAge;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "weight", precision = 5, scale = 2, nullable = false)
    private Double weight;

    @Column(name = "height", precision = 5, scale = 2, nullable = false)
    private Double height;

    @Column(name = "gender", length = 50, nullable = false)
    private String gender;

    @Column(name = "allergies", length = 250)
    private String allergies;

    @Column(name = "behavior_notes", length = 250)
    private String behaviorNotes;

    // Relación con Users
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    // Relación con ImageS3
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", referencedColumnName = "image_id", nullable = false)
    private ImageS3 image;

    // Relación con VaccinationSchedule
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VaccinationSchedule> vaccinationSchedules;

    // Relación con MedicalHistory
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalHistory> medicalHistories;
}
