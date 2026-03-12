package com.example.trackmint.services;

import com.example.trackmint.dto.MonthlySummaryResponse;
import com.example.trackmint.dto.TransactionRequest;
import com.example.trackmint.dto.TransactionResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    TransactionResponse createTransaction(TransactionRequest request, Long userId);

    TransactionResponse getTransactionById(Long transactionId, Long userId);

    TransactionResponse updateTransaction(Long transactionId, Long userId, TransactionRequest request);

    void deleteTransaction(Long transactionId, Long userId);

    List<TransactionResponse> getTransactionsForUser(Long userId);

    List<TransactionResponse> getTransactionDateRange(Long userId, LocalDate start, LocalDate end);

    List<TransactionResponse> getTransactionsByCategory(Long userId, Long categoryId);
    MonthlySummaryResponse getMonthlySummary(Long userId, int year, int month);

    BigDecimal getTotalSpentByCategory(Long userId, Long categoryId);
}
