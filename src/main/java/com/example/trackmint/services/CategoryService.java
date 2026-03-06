package com.example.trackmint.services;

import com.example.trackmint.dto.CategoryRequest;
import com.example.trackmint.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request, Long userId);
    List<CategoryResponse> getUserCategories(Long  userId);
}
