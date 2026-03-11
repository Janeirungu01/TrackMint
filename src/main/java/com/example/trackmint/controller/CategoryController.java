package com.example.trackmint.controller;

import com.example.trackmint.dto.CategoryRequest;
import com.example.trackmint.dto.CategoryResponse;
import com.example.trackmint.dto.TransactionResponse;
import com.example.trackmint.security.CustomUserDetails;
import com.example.trackmint.services.CategoryService;
import com.example.trackmint.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final TransactionService transactionService;

    @PostMapping
    public CategoryResponse createCategory(
            @RequestBody CategoryRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getId();
        return categoryService.createCategory(request, userId);
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getId();
        return categoryService.getUserCategories(userId);
    }

    @GetMapping("/{categoryId}/transactions")
    public List<TransactionResponse> getTransactionsByCategory(
            @PathVariable Long categoryId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return transactionService.getTransactionsByCategory(userDetails.getId(), categoryId);
    }

    @GetMapping("/{categoryId}/total")
    public BigDecimal getTotalSpentByCategory(
            @PathVariable Long categoryId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return transactionService.getTotalSpentByCategory(userDetails.getId(), categoryId);


    }
}

