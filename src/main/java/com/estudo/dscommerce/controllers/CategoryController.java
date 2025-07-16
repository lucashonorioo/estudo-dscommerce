package com.estudo.dscommerce.controllers;

import com.estudo.dscommerce.dto.response.CategoryResponseDTO;
import com.estudo.dscommerce.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAll(){
        List<CategoryResponseDTO> categoryResponseDTO = categoryService.findAll();
        return ResponseEntity.ok().body(categoryResponseDTO);
    }
}
