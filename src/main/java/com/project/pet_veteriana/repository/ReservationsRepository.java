package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationsRepository extends JpaRepository<Reservations, Integer> {
}
