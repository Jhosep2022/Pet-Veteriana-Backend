package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Pets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetsRepository extends JpaRepository<Pets, Integer> {
}
