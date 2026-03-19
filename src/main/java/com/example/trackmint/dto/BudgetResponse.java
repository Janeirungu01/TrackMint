package com.example.trackmint.dto;

import java.math.BigDecimal;

public record BudgetResponse(
        Long id,
//        Long
        String categoryName,
        BigDecimal monthlyLimit,
        String month,
        BigDecimal spent,
        BigDecimal percentageUsed,
        String status
) {
}
