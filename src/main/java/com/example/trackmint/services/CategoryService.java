package com.example.trackmint.services;

import com.example.trackmint.dto.CategoryRequest;
import com.example.trackmint.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryRequest request, Long userId);
    List<Category> getUserCategories(Long  userId);
}
