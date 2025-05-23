package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.TransactionHistoryDto;
import com.project.pet_veteriana.entity.TransactionHistory;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.entity.Services;
import com.project.pet_veteriana.entity.Products;
import com.project.pet_veteriana.repository.TransactionHistoryRepository;
import com.project.pet_veteriana.repository.UsersRepository;
import com.project.pet_veteriana.repository.ServicesRepository;
import com.project.pet_veteriana.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionHistoryBl {

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private ProductsRepository productsRepository;

    public TransactionHistoryDto createTransactionHistory(TransactionHistoryDto dto) {
        Users user = usersRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Services service = null;
        if (dto.getServiceId() != null) {
            service = servicesRepository.findById(dto.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Service not found"));
        }

        Products product = null;
        if (dto.getProductId() != null) {
            product = productsRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
        }

        TransactionHistory transaction = new TransactionHistory();
        transaction.setTotalAmount(dto.getTotalAmount());
        transaction.setQuantity(dto.getQuantity());
        transaction.setAmountPerUnit(dto.getAmountPerUnit());
        transaction.setDetail(dto.getDetail());
        transaction.setStatus("pendiente");
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUser(user);
        transaction.setService(service);
        transaction.setProduct(product);

        TransactionHistory savedTransaction = transactionHistoryRepository.save(transaction);
        return convertToDto(savedTransaction);
    }

    public List<TransactionHistoryDto> getAllTransactionHistories() {
        return transactionHistoryRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TransactionHistoryDto getTransactionHistoryById(Integer id) {
        TransactionHistory transaction = transactionHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction History not found"));
        return convertToDto(transaction);
    }

    public List<TransactionHistoryDto> getTransactionHistoriesByUserId(Integer userId) {
        List<TransactionHistory> transactions = transactionHistoryRepository.findByUserUserId(userId);
        return transactions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TransactionHistoryDto> getTransactionHistoriesByProviderId(Integer providerId) {
        List<TransactionHistory> transactions = transactionHistoryRepository.findByProviderId(providerId);

        if (transactions.isEmpty()) {
            return List.of(); // Devuelve lista vacía en lugar de lanzar excepción
        }

        return transactions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TransactionHistoryDto updateTransactionHistory(Integer id, TransactionHistoryDto dto) {
        TransactionHistory transaction = transactionHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction History not found"));

        Users user = usersRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Services service = null;
        if (dto.getServiceId() != null) {
            service = servicesRepository.findById(dto.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Service not found"));
        }

        Products product = null;
        if (dto.getProductId() != null) {
            product = productsRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
        }

        transaction.setTotalAmount(dto.getTotalAmount());
        transaction.setQuantity(dto.getQuantity());
        transaction.setAmountPerUnit(dto.getAmountPerUnit());
        transaction.setDetail(dto.getDetail());
        transaction.setStatus(dto.getStatus());
        transaction.setUser(user);
        transaction.setService(service);
        transaction.setProduct(product);

        TransactionHistory updatedTransaction = transactionHistoryRepository.save(transaction);
        return convertToDto(updatedTransaction);
    }

    public boolean deleteTransactionHistory(Integer id) {
        if (transactionHistoryRepository.existsById(id)) {
            transactionHistoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public TransactionHistoryDto updateTransactionStatus(Integer id, String status) {
        TransactionHistory transaction = transactionHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Validar que el nuevo estado sea válido
        if (!status.equals("pendiente") && !status.equals("atendido") && !status.equals("cancelado")) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        transaction.setStatus(status);
        TransactionHistory updatedTransaction = transactionHistoryRepository.save(transaction);
        return convertToDto(updatedTransaction);
    }

    private TransactionHistoryDto convertToDto(TransactionHistory transaction) {
        return new TransactionHistoryDto(
                transaction.getTransactionHistoryId(),
                transaction.getTotalAmount(),
                transaction.getStatus(),
                transaction.getCreatedAt(),
                transaction.getUser().getUserId(),
                transaction.getService() != null ? transaction.getService().getServiceId() : null,
                transaction.getProduct() != null ? transaction.getProduct().getProductId() : null,
                transaction.getQuantity(),
                transaction.getAmountPerUnit(),
                transaction.getDetail()
        );
    }
}
