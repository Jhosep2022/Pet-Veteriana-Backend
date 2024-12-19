package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "Offers_Products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OffersProducts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offers_products_id", nullable = false)
    private Integer offersProductsId;

    @ManyToOne
    @JoinColumn(name = "Offers_offer_id", referencedColumnName = "offer_id", nullable = false)
    private Offers offer;

    @ManyToOne
    @JoinColumn(name = "Products_product_id", referencedColumnName = "product_id", nullable = false)
    private Products product;
}