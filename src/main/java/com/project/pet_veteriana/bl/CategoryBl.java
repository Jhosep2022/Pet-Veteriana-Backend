package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.CategoryDto;
import com.project.pet_veteriana.entity.Category;
import com.project.pet_veteriana.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryBl {

    @Autowired
    private CategoryRepository categoryRepository;

    // Crear una nueva categoría
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setNameCategory(categoryDto.getNameCategory());
        category.setCreatedAt(LocalDateTime.now());

        Category savedCategory = categoryRepository.save(category);
        return convertToDto(savedCategory);
    }

    // Obtener todas las categorías
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtener categoría por ID
    public CategoryDto getCategoryById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(this::convertToDto).orElse(null);
    }

    // Actualizar categoría
    public CategoryDto updateCategory(Integer id, CategoryDto categoryDto) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setNameCategory(categoryDto.getNameCategory());
            category.setCreatedAt(LocalDateTime.now());

            Category updatedCategory = categoryRepository.save(category);
            return convertToDto(updatedCategory);
        }
        return null;
    }

    // Eliminar categoría
    public boolean deleteCategory(Integer id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Convertir Category a CategoryDto
    private CategoryDto convertToDto(Category category) {
        return new CategoryDto(
                category.getCategoryId(),
                category.getNameCategory(),
                category.getCreatedAt());
    }
}
