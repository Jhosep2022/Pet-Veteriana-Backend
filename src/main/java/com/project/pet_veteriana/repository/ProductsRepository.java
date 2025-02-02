package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Products;
import com.project.pet_veteriana.entity.Providers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
    // Obtener los 10 productos más recientes
    List<Products> findTop10ByOrderByCreatedAtDesc();

    // Obtener todos los productos de un proveedor específico
    List<Products> findByProvider(Providers provider);

    int countByProvider(Providers provider);
}
