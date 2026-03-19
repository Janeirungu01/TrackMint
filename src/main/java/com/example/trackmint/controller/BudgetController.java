package com.example.trackmint.controller;

import com.example.trackmint.dto.BudgetRequest;
import com.example.trackmint.dto.BudgetResponse;
import com.example.trackmint.dto.BudgetStatusResponse;
import com.example.trackmint.model.Category;
import com.example.trackmint.model.User;
import com.example.trackmint.security.CustomUserDetails;
import com.example.trackmint.services.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    @PostMapping
    public BudgetResponse createBudget(
            @RequestBody @Valid BudgetRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getId();
        return budgetService.createBudget(request, userId);
    }

    @GetMapping
    public List<BudgetStatusResponse> getBudgets(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return budgetService.getBudgetSummary(userDetails.getId());
    }

//    @GetMapping("/check")

    @GetMapping("/check")
    public BudgetStatusResponse checkBudget(
            @RequestParam Long categoryId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = new User();
        user.setId(userDetails.getId());

        Category category = new Category();
        category.setId(categoryId);

        return budgetService.checkBudgetExceeded(user, category);
    }

    @GetMapping("/summary")
    public List<BudgetStatusResponse> getBudgetSummary(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return budgetService.getBudgetSummary(userDetails.getId());
    }

    }
