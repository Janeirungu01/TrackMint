package com.example.trackmint.controller;

import com.example.trackmint.dto.TransactionRequest;
import com.example.trackmint.dto.TransactionResponse;
import com.example.trackmint.model.Transaction;
import com.example.trackmint.model.User;
import com.example.trackmint.repository.UserRepository;
import com.example.trackmint.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final UserRepository userRepository;
    private final TransactionService transactionService;

    @PostMapping
    public TransactionResponse createTransaction(@RequestBody TransactionRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return transactionService.createTransaction(request, user.getId());
    }
    @GetMapping
    public List<TransactionResponse> getUserTransactions() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Call service method to fetch transactions for this user
        return transactionService.getTransactionsForUser(user.getId());
    }

}
