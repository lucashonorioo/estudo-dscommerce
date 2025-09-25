package com.estudo.dscommerce.services.impl;

import com.estudo.dscommerce.dto.response.CategoryResponseDTO;
import com.estudo.dscommerce.model.Category;
import com.estudo.dscommerce.repositories.CategoryRepository;
import com.estudo.dscommerce.tests.CategoryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

    @InjectMocks
    private CategoryServiceImpl service;

    @Mock
    private CategoryRepository categoryRepository;

    private Category category;
    private List<Category> list;

    @BeforeEach
    void setUp() throws Exception{
        category = CategoryFactory.createCategory();

        list = new ArrayList<>();
        list.add(category);

        Mockito.when(categoryRepository.findAll()).thenReturn(list);
    }

    @Test
    public void findAllShouldReturnListCategoryDTO(){

        List<CategoryResponseDTO> result = service.findAll();

        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(result.get(0).getId(), category.getId());
        Assertions.assertEquals(result.get(0).getName(), category.getName());
    }

}
