package com.estudo.dscommerce.dto.request;

import com.estudo.dscommerce.model.Category;

public class CategoryRequestDTO {

    private Long id;
    private String name;

    public CategoryRequestDTO(){

    }

    public CategoryRequestDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryRequestDTO(Category category) {
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
