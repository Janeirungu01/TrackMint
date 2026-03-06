package com.example.trackmint.dto;

import com.example.trackmint.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class TransactionResponse {
    private long id;
    private BigDecimal amount;
    private TransactionType type;
    private String description;
    private LocalDate date;
    private Long categoryId;
    private String categoryName;
}
