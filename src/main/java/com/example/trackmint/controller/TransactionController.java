package com.example.trackmint.controller;

import com.example.trackmint.dto.MonthlySummaryResponse;
import com.example.trackmint.dto.TransactionRequest;
import com.example.trackmint.dto.TransactionResponse;
import com.example.trackmint.security.CustomUserDetails;
import com.example.trackmint.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public TransactionResponse createTransaction(
            @RequestBody TransactionRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return transactionService.createTransaction(request, userDetails.getId());
    }

    @GetMapping
    public List<TransactionResponse> getUserTransactions(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return transactionService.getTransactionsForUser(userDetails.getId());
    }

    @GetMapping("/{id}")
    public TransactionResponse getTransactionById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return transactionService.getTransactionById(id, userDetails.getId());
    }

    @PutMapping("/{id}")
    public TransactionResponse updateTransaction(
            @PathVariable Long id,
            @RequestBody TransactionRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return transactionService.updateTransaction(id, userDetails.getId(), request);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        transactionService.deleteTransaction(id, userDetails.getId());
    }

    @GetMapping("/date-range")
    public List<TransactionResponse> getTransactionDateRange(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        return transactionService.getTransactionDateRange(userDetails.getId(), start, end);
    }

    @GetMapping("/monthly-summary")
    public MonthlySummaryResponse getMonthlySummary(
            @RequestParam int year,
            @RequestParam int month,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return transactionService.getMonthlySummary(userDetails.getId(), year, month);
    }



}
