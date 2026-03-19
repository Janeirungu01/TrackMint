package com.example.trackmint.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.YearMonth;

@Getter
@Setter
@Entity
@Table(name = "budget")
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
