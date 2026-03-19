package com.example.trackmint.repository;

import com.example.trackmint.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.trackmint.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser_Id(Long userId);
    Optional<Category> findByIdAndUser_Id(Long id, Long userId);
    Optional<Category> findByNameAndUser(String name, User user);
}
