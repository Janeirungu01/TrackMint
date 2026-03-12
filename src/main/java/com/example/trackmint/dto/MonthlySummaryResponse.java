package com.example.trackmint.dto;

import java.math.BigDecimal;

public record MonthlySummaryResponse (
        BigDecimal income,
        BigDecimal expenses,
        BigDecimal balance
){}
