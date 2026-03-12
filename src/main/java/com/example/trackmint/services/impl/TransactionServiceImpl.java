    package com.example.trackmint.services.impl;
    
    import com.example.trackmint.dto.MonthlySummaryResponse;
    import com.example.trackmint.dto.TransactionRequest;
    import com.example.trackmint.dto.TransactionResponse;
    import com.example.trackmint.exception.TransactionNotFoundException;
    import com.example.trackmint.model.Category;
    import com.example.trackmint.model.Transaction;
    import com.example.trackmint.model.User;
    import com.example.trackmint.repository.CategoryRepository;
    import com.example.trackmint.repository.TransactionRepository;
    import com.example.trackmint.repository.UserRepository;
    import com.example.trackmint.services.TransactionService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    
    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.util.List;
    @Service
    @RequiredArgsConstructor
    public class TransactionServiceImpl implements TransactionService {
    
        private final UserRepository userRepository;
        private final TransactionRepository transactionRepository;
        private final CategoryRepository categoryRepository;
    
        @Override
        public TransactionResponse createTransaction(TransactionRequest request, Long userId) {
    
            Category category = categoryRepository
                    .findByIdAndUser_Id(request.getCategoryId(), userId)
                    .orElseThrow(() -> new TransactionNotFoundException("Category not found for this user"));
    
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
    
            Transaction transaction = new Transaction();
            transaction.setAmount(request.getAmount());
            transaction.setType(request.getType());
            transaction.setDescription(request.getDescription());
            transaction.setDate(request.getDate());
            transaction.setUser(user);
            transaction.setCategory(category);
    
            Transaction saved = transactionRepository.save(transaction);
    
            return mapToResponse(saved);
        }
    
        //Get Transaction by id
        @Override
        public TransactionResponse getTransactionById(Long transactionId, Long userId) {
            Transaction transaction = transactionRepository
                    .findByIdAndUser_Id(transactionId, userId)
                    .orElseThrow(()-> new TransactionNotFoundException("Transaction not found"));
    
            return mapToResponse(transaction);
        }
    
        //Update Transaction
        @Override
        public TransactionResponse updateTransaction(Long transactionId, Long userId, TransactionRequest request) {
            Transaction transaction = transactionRepository
                    .findByIdAndUser_Id(transactionId, userId)
                    .orElseThrow(()-> new TransactionNotFoundException("Transaction not found"));
    
            transaction.setDescription(request.getDescription());
            transaction.setAmount(request.getAmount());
            transaction.setDate(request.getDate());
    
            Transaction updated = transactionRepository.save(transaction);
            return mapToResponse(updated);
        }
    
        //Delete Transaction
        @Override
        public void deleteTransaction(Long transactionId, Long userId) {
            Transaction transaction = transactionRepository
                    .findByIdAndUser_Id(transactionId, userId)
                    .orElseThrow(()-> new TransactionNotFoundException("Transaction not found"));
            transactionRepository.delete(transaction);
    
        }
    
        //Get Transactions Date Range
        public List<TransactionResponse> getTransactionDateRange(Long userId, LocalDate start, LocalDate end) {
            return transactionRepository
                    .findByUser_IdAndDateBetween(userId, start, end)
                    .stream()
                    .map(this::mapToResponse)
                    .toList();
    
        }
    
        @Override
        public List<TransactionResponse> getTransactionsForUser(Long userId) {
            List<Transaction> transactions = transactionRepository.findByUser_Id(userId);
            return transactions.stream()
                    .map(this::mapToResponse)
                    .toList();
        }
    
        @Override
        public List<TransactionResponse> getTransactionsByCategory(Long userId, Long categoryId) {
            // Ensure category belongs to user
            categoryRepository.findByIdAndUser_Id(categoryId, userId)
                    .orElseThrow(() -> new TransactionNotFoundException("Category not found for this user"));
    
            List<Transaction> transactions = transactionRepository.findByCategory_Id(categoryId);
    
            return transactions.stream()
                    .map(this::mapToResponse)
                    .toList();
        }
    
        @Override
        public BigDecimal getTotalSpentByCategory(Long userId, Long categoryId) {
            // Ensure category belongs to user
            categoryRepository.findByIdAndUser_Id(categoryId, userId)
                    .orElseThrow(() -> new TransactionNotFoundException("Category not found for this user"));
    
            return transactionRepository.findByCategory_Id(categoryId)
                    .stream()
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    
        public MonthlySummaryResponse getMonthlySummary(
                Long userId,
                int year,
                int month) {
            BigDecimal income = transactionRepository.getTotalIncomeForMonth(userId, year, month);
            BigDecimal expenses = transactionRepository.getTotalExpensesForMonth(userId, year, month);
            BigDecimal balance = income.subtract(expenses);
            return new MonthlySummaryResponse(income, expenses, balance);
        }
    
        private TransactionResponse mapToResponse(Transaction transaction) {
            return TransactionResponse.builder()
                    .id(transaction.getId())
                    .amount(transaction.getAmount())
                    .type(transaction.getType())
                    .description(transaction.getDescription())
                    .date(transaction.getDate())
                    .categoryId(transaction.getCategory().getId())
                    .categoryName(transaction.getCategory().getName())
                    .build();
        }
    }
    
