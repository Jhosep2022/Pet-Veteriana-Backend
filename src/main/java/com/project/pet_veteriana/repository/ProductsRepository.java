package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
}
