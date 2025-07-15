package com.estudo.dscommerce.dto.response;

import com.estudo.dscommerce.model.Category;
import com.estudo.dscommerce.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;

    private List<CategoryResponseDTO> categories = new ArrayList<>();

    public ProductResponseDTO(){

    }

    public ProductResponseDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductResponseDTO(Product product) {
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();
        imgUrl = product.getImgUrl();
        for(Category category : product.getCategories()){
            categories.add(new CategoryResponseDTO(category));
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<CategoryResponseDTO> getCategories() {
        return categories;
    }
}
