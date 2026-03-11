package com.example.trackmint.repository;

import com.example.trackmint.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser_Id(Long userId);
    List<Transaction> findByCategory_Id(Long categoryId);

    List<Transaction> findByUser_IdAndDateBetween(Long userId, LocalDate start, LocalDate end);

}
