package com.estudo.dscommerce.services;

import com.estudo.dscommerce.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDTO> findAll();
}
