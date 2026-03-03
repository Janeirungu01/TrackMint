package com.example.trackmint.services.impl;

import com.example.trackmint.dto.CategoryRequest;
import com.example.trackmint.model.Category;
import com.example.trackmint.repository.CategoryRepository;
import com.example.trackmint.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createCategory (CategoryRequest request, Long userId) {
        Category category = new Category();
        category.setName(request.getName());
        category.setUserid(userId);

        return categoryRepository.save(category);
    }
    @Override
    public List<Category> getUserCategories(Long userId) {
        return categoryRepository.findByUserIdOrUserIdIsNull(userId);
    }

}
