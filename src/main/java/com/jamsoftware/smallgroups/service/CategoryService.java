package com.jamsoftware.smallgroups.service;

import com.jamsoftware.smallgroups.model.Category;
import com.jamsoftware.smallgroups.model.CustomUserDetails;
import com.jamsoftware.smallgroups.repository.CategoryRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private List<Category> allCategories;
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        if (allCategories == null) {
            allCategories = categoryRepository.findAllCategories();
        }
        return allCategories;
    }
}
