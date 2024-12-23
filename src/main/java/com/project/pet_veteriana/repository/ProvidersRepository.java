package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Providers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvidersRepository extends JpaRepository<Providers, Integer> {

    List<Providers> findByStatusTrue();

}
