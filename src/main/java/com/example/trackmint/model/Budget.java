package com.example.trackmint.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.YearMonth;

public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal monthlyLimit;

    private YearMonth month;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;


}
