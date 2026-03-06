package com.example.trackmint.services;

import com.example.trackmint.dto.TransactionRequest;
import com.example.trackmint.dto.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request, Long userId);
    List<TransactionResponse> getTransactionsForUser(Long userId);
}
