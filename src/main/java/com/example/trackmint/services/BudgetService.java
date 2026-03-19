package com.example.trackmint.services;

import com.example.trackmint.dto.BudgetRequest;
import com.example.trackmint.dto.BudgetResponse;
import com.example.trackmint.dto.BudgetStatusResponse;
import com.example.trackmint.model.Category;
import com.example.trackmint.model.User;

import java.util.List;

public interface BudgetService {
    BudgetResponse createBudget(BudgetRequest request, Long userId);
    BudgetStatusResponse checkBudgetExceeded(User user, Category category);
    List<BudgetStatusResponse> getBudgetSummary(Long userId);

}
