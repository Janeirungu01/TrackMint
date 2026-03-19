package com.example.trackmint.repository;

import com.example.trackmint.model.Budget;
import com.example.trackmint.model.Category;
import com.example.trackmint.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByUserAndCategoryAndMonth (
            User user,
     Category category,
    YearMonth month);
    List<Budget> findByUser_Id (Long id);
}

