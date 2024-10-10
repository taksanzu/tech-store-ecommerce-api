package org.tak.techstoreecommerce.service;

import jakarta.validation.Valid;
import org.tak.techstoreecommerce.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Long categoryId);

    CategoryDTO addCategory(@Valid CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long categoryId, @Valid CategoryDTO categoryDTO);

    String deleteCategory(Long categoryId);
}
