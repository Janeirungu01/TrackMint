package com.example.trackmint.dto;

import com.example.trackmint.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter

public class TransactionRequest {
    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private TransactionType type;

    private String description;

    private LocalDate date;

    @NotNull
    private Long categoryId;
}
