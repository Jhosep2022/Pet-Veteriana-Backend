package com.project.pet_veteriana.repository;

import com.project.pet_veteriana.entity.Services;
import com.project.pet_veteriana.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {

    void deleteByService(Services service);

}
