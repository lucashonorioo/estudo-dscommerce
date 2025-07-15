package com.estudo.dscommerce.dto.response;

import com.estudo.dscommerce.model.Category;
import com.estudo.dscommerce.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductResponseMinDTO {

    private Long id;
    private String name;
    private Double price;
    private String imgUrl;

    private List<CategoryResponseDTO> categories = new ArrayList<>();

    public ProductResponseMinDTO(){

    }

    public ProductResponseMinDTO(Long id, String name, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductResponseMinDTO(Product product) {
        id = product.getId();
        name = product.getName();
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
