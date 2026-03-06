package com.example.trackmint.controller;

import com.example.trackmint.dto.CategoryRequest;
import com.example.trackmint.dto.CategoryResponse;
import com.example.trackmint.security.CustomUserDetails;
import com.example.trackmint.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryResponse createCategory(
            @RequestBody CategoryRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getId();
        return categoryService.createCategory(request, userId);
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getId();
        return categoryService.getUserCategories(userId);
    }
}

