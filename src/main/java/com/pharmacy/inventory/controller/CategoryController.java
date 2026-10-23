package com.pharmacy.inventory.controller;

import com.pharmacy.inventory.dto.ApiResponse;
import com.pharmacy.inventory.entity.Category;
import com.pharmacy.inventory.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        return ResponseEntity.ok(ApiResponse.success(categoryRepository.findAll()));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody Category category) {
        Category saved = categoryRepository.save(category);
        return ResponseEntity.ok(ApiResponse.success("Category added", saved));
    }
}
