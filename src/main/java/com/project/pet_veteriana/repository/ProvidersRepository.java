package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Providers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProvidersRepository extends JpaRepository<Providers, Integer> {

    List<Providers> findByStatusTrue();

    boolean existsByUser_UserId(Integer userId);

    List<Providers> findTop5ByStatusTrueOrderByRatingDesc();

    List<Providers> findTop10ByStatusTrueOrderByCreatedAtDesc();

    // Nuevo método para obtener un proveedor por userId
    Optional<Providers> findByUser_UserId(Integer userId);
}
