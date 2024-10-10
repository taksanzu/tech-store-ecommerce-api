package org.tak.techstoreecommerce.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tak.techstoreecommerce.dto.CategoryDTO;
import org.tak.techstoreecommerce.exception.APIException;
import org.tak.techstoreecommerce.exception.ResourceNotFoundException;
import org.tak.techstoreecommerce.model.Category;
import org.tak.techstoreecommerce.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> category = categoryRepository.findAll();
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("No categories found");
        }
        List<CategoryDTO> categoryDTO = category.stream()
                .map(category1 -> modelMapper.map(category1, CategoryDTO.class))
                .collect(Collectors.toList());
        return categoryDTO;

    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsCategoriesByCategoryName(categoryDTO.getCategoryName())) {
            throw new APIException("Category already exists");
        }

        Category category = modelMapper.map(categoryDTO, Category.class);
        Category newCategory = categoryRepository.save(category);
        return modelMapper.map(newCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

        category.setCategoryName(categoryDTO.getCategoryName());
        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        categoryRepository.delete(category);
        return "Category with id " + categoryId + " deleted successfully";
    }

}
