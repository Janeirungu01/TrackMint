package com.example.trackmint.repository;

import com.example.trackmint.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser_Id(Long userId);
    List<Transaction> findByCategory_Id(Long categoryId);
    Optional<Transaction> findByIdAndUser_Id(Long id, Long userId);

    List<Transaction> findByUser_IdAndDateBetween(
            Long userId,
            LocalDate start,
            LocalDate end
    );

    @Query("""
SELECT COALESCE(SUM(t.amount),0)
FROM Transaction t
WHERE t.user.id = :userId
AND t.type = 'INCOME'
AND YEAR(t.date) = :year
AND MONTH(t.date) = :month
""")
    BigDecimal getTotalIncomeForMonth(Long userId, int year, int month);
    @Query("""
SELECT COALESCE(SUM(t.amount),0)
FROM Transaction t
WHERE t.user.id = :userId
AND t.type = 'EXPENSE'
AND YEAR(t.date) = :year
AND MONTH(t.date) = :month
""")
    BigDecimal getTotalExpensesForMonth(Long userId, int year, int month);

    @Query("""
SELECT MONTH(t.date), SUM(t.amount)
FROM Transaction t
WHERE t.user.id = :userId
GROUP BY MONTH(t.date)
ORDER BY MONTH(t.date)
""")
    List<Object[]> getMonthlySpending(Long userId);
}
