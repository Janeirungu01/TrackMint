package com.example.trackmint.services.impl;

import com.example.trackmint.dto.CategoryRequest;
import com.example.trackmint.dto.CategoryResponse;
import com.example.trackmint.model.Category;
import com.example.trackmint.model.User;
import com.example.trackmint.repository.CategoryRepository;
import com.example.trackmint.repository.UserRepository;
import com.example.trackmint.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory (CategoryRequest request, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = new Category();
        category.setName(request.getName());
        category.setUser(user);

        Category saved = categoryRepository.save(category);

        return mapToResponse(saved);
    }
    @Override
    public List<CategoryResponse> getUserCategories(Long userId) {
        return categoryRepository.findByUser_Id(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
