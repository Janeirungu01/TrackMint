package com.example.trackmint.exception;


public class BudgetNotFoundException extends RuntimeException {

    public BudgetNotFoundException() {
        super("Budget not set for this category");
    }
}