package com.example.trackmint.dto;

import java.math.BigDecimal;

public record BudgetStatusResponse(
        String categoryName,
        BigDecimal limit,
        BigDecimal spent,
        BigDecimal remaining,
        BigDecimal percentageUsed,
        String status
) {}
