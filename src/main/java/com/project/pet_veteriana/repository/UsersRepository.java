package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
}