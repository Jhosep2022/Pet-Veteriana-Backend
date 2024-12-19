package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Medical_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_history_id", nullable = false)
    private Integer medicalHistoryId;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "visit_reason", nullable = false, length = 150)
    private String visitReason;

    @Column(name = "symptoms", nullable = false, length = 150)
    private String symptoms;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relación con Pets
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pets pet;
}
