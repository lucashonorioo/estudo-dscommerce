package com.estudo.dscommerce.services.impl;

import com.estudo.dscommerce.dto.response.CategoryResponseDTO;
import com.estudo.dscommerce.model.Category;
import com.estudo.dscommerce.repositories.CategoryRepository;
import com.estudo.dscommerce.services.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map( c -> new CategoryResponseDTO(c)).toList();
    }
}
