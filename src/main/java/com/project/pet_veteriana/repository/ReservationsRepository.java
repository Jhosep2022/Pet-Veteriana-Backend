package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Reservations;
import com.project.pet_veteriana.entity.ServiceAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationsRepository extends JpaRepository<Reservations, Integer> {

    boolean existsByAvailability(ServiceAvailability availability);

    List<Reservations> findByUser_UserId(Integer userId);

    List<Reservations> findByService_ServiceId(Integer serviceId);
}
