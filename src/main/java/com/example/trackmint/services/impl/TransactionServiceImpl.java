package com.example.trackmint.services.impl;

import com.example.trackmint.dto.TransactionRequest;
import com.example.trackmint.model.Category;
import com.example.trackmint.model.Transaction;
import com.example.trackmint.repository.CategoryRepository;
import com.example.trackmint.repository.TransactionRepository;
import com.example.trackmint.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public Transaction createTransaction(TransactionRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());
        transaction.setUserId(userId);
        transaction.setCategory(category);
        return transactionRepository.save(transaction);

    }

}
