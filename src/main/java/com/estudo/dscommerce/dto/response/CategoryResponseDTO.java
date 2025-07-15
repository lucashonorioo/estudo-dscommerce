package com.estudo.dscommerce.dto.response;

import com.estudo.dscommerce.model.Category;

public class CategoryResponseDTO {

    private Long id;
    private String name;

    public CategoryResponseDTO(){

    }

    public CategoryResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryResponseDTO(Category category) {
        id = category.getId();
        name = category.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
