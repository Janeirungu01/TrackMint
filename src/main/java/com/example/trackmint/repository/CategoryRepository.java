package com.example.trackmint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.trackmint.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserIdOrUserIdIsNull(Long userId);
}
