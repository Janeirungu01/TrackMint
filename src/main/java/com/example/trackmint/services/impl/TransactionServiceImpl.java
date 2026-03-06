package com.example.trackmint.services.impl;

import com.example.trackmint.dto.TransactionRequest;
import com.example.trackmint.dto.TransactionResponse;
import com.example.trackmint.model.Category;
import com.example.trackmint.model.Transaction;
import com.example.trackmint.model.User;
import com.example.trackmint.repository.CategoryRepository;
import com.example.trackmint.repository.TransactionRepository;
import com.example.trackmint.repository.UserRepository;
import com.example.trackmint.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public TransactionResponse createTransaction(TransactionRequest request, Long userId) {

        Category category = categoryRepository
                .findByIdAndUser_Id(request.getCategoryId(), userId)
                .orElseThrow(() -> new RuntimeException("Category not found for this user"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());
        transaction.setUser(user);
        transaction.setCategory(category);

        Transaction saved = transactionRepository.save(transaction);

        return mapToResponse(saved);
    }

    @Override
    public List<TransactionResponse> getTransactionsForUser(Long userId) {

        List<Transaction> transactions = transactionRepository.findByUser_Id(userId);

        return transactions.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .description(transaction.getDescription())
                .date(transaction.getDate())
                .categoryId(transaction.getCategory().getId())
                .categoryName(transaction.getCategory().getName())
                .build();
    }
}