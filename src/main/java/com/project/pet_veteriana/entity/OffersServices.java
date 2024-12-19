package com.project.pet_veteriana.entity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "Offers_Services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OffersServices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offers_services_id", nullable = false)
    private Integer offersServicesId;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "service_id", nullable = false)
    private Services service;

    @ManyToOne
    @JoinColumn(name = "offer_id", referencedColumnName = "offer_id", nullable = false)
    private Offers offer;
}