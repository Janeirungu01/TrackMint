package com.example.trackmint.controller;

import com.example.trackmint.dto.CategoryRequest;
import com.example.trackmint.model.Category;
import com.example.trackmint.services.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private  final CategoryServiceImpl categoryService;

    @PostMapping
    public Category createCategory(@Valid @RequestBody CategoryRequest request) {
        Long userId = 1L; // replace later with authenticated user
        return categoryService.createCategory(request, userId);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        Long userId = 1L;
        return categoryService.getUserCategories(userId);
    }
}

