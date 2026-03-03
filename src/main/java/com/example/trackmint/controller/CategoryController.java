package com.example.trackmint.controller;

import com.example.trackmint.dto.CategoryRequest;
import com.example.trackmint.model.Category;
import com.example.trackmint.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public Category createCategory(@RequestBody CategoryRequest request) {
        Long userId = 1L; // replace later with authenticated user
        return categoryService.createCategory(request, userId);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        Long userId = 1L;
        return categoryService.getUserCategories(userId);
    }
}

