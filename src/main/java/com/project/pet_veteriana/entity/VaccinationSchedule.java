package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Vaccination_schedule")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccination_schedule_id", nullable = false)
    private Integer vaccinationScheduleId;

    @Column(name = "date_vaccination", nullable = false)
    private LocalDateTime dateVaccination;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relación con Pets
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pets pet;

    // Relación con Vaccination
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccination_id", nullable = false)
    private Vaccination vaccination;
}
