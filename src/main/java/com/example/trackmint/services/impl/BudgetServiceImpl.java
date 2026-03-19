package com.example.trackmint.services.impl;

import com.example.trackmint.dto.BudgetRequest;
import com.example.trackmint.dto.BudgetResponse;
import com.example.trackmint.dto.BudgetStatusResponse;
import com.example.trackmint.exception.CategoryNotFoundException;
import com.example.trackmint.exception.UserNotFoundException;
import com.example.trackmint.model.Budget;
import com.example.trackmint.model.Category;
import com.example.trackmint.model.User;
import com.example.trackmint.repository.BudgetRepository;
import com.example.trackmint.repository.CategoryRepository;
import com.example.trackmint.repository.TransactionRepository;
import com.example.trackmint.repository.UserRepository;
import com.example.trackmint.services.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    // ================= CREATE =================
    @Override
    public BudgetResponse createBudget(BudgetRequest request, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        Budget budget = new Budget();
        budget.setUser(user);
        budget.setCategory(category);
        budget.setMonthlyLimit(request.monthlyLimit());
        budget.setMonth(request.month());

        Budget saved = budgetRepository.save(budget);

        return new BudgetResponse(
                saved.getId(),
                saved.getCategory().getName(),
                saved.getMonthlyLimit(),
                saved.getMonth().toString(),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                "SAFE"
        );
    }

    // ================= CHECK SINGLE =================
    @Override
    public BudgetStatusResponse checkBudgetExceeded(User user, Category category) {

        YearMonth currentMonth = YearMonth.now();

        Budget budget = budgetRepository
                .findByUserAndCategoryAndMonth(user, category, currentMonth)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        BigDecimal spent = getSpentForMonth(user, category, currentMonth);

        return buildResponse(budget, spent);
    }

    // ================= SUMMARY =================
    @Override
    public List<BudgetStatusResponse> getBudgetSummary(Long userId) {

        List<Budget> budgets = budgetRepository.findByUser_Id(userId);

        return budgets.stream()
                .map(budget -> {
                    BigDecimal spent = getSpentForMonth(
                            budget.getUser(),
                            budget.getCategory(),
                            budget.getMonth()
                    );
                    return buildResponse(budget, spent);
                })
                .toList();
    }

    // ================= HELPER METHODS =================

    /**
     * Gets total spent using DATE RANGE (safe across all DBs)
     */
    private BigDecimal getSpentForMonth(User user, Category category, YearMonth ym) {

        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        BigDecimal spent = transactionRepository
                .sumExpensesByCategoryAndUserAndMonth(
                        category,
                        user,
                        start,
                        end
                );

        return spent != null ? spent : BigDecimal.ZERO;
    }

//     Builds consistent response (avoids duplication)
    private BudgetStatusResponse buildResponse(Budget budget, BigDecimal spent) {

        BigDecimal limit = budget.getMonthlyLimit();

        BigDecimal remaining = limit.subtract(spent);

        BigDecimal percentage = BigDecimal.ZERO;

        if (limit != null && limit.compareTo(BigDecimal.ZERO) > 0) {
            percentage = spent
                    .divide(limit, 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        String status;
        if (percentage.compareTo(BigDecimal.valueOf(100)) > 0) {
            status = "EXCEEDED";
        } else if (percentage.compareTo(BigDecimal.valueOf(80)) > 0) {
            status = "WARNING";
        } else {
            status = "SAFE";
        }

        return new BudgetStatusResponse(
                budget.getCategory().getName(),
                limit,
                spent,
                remaining,
                percentage,
                status
        );
    }
}