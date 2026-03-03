package com.example.trackmint.services;

import com.example.trackmint.dto.TransactionRequest;
import com.example.trackmint.model.Transaction;

public interface TransactionService {
    Transaction createTransaction(TransactionRequest request, Long userId);
}
