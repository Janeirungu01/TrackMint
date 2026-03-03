package com.example.trackmint.controller;

import com.example.trackmint.dto.TransactionRequest;
import com.example.trackmint.model.Transaction;
import com.example.trackmint.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public Transaction createTransaction(@RequestBody TransactionRequest request) {
        Long userId = 1L;
        return transactionService.createTransaction(request, userId);

    }

}
