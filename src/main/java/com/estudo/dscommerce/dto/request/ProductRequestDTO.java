package com.estudo.dscommerce.dto.request;

import com.estudo.dscommerce.model.Category;
import com.estudo.dscommerce.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class ProductRequestDTO {

    @NotBlank(message = "Campo requerido")
    @Size(min = 3, max = 80, message = "O nome precisa ter entre 3 a 80 caracteres")
    private String name;

    @NotBlank(message = "Campo requerido")
    @Size(min = 10, message = "A descrição precisa ter no minimo 10 caracteres")
    private String description;

    @Positive(message = "O preço deve ser positivo")
    private Double price;

    private String imgUrl;

    @NotEmpty(message = "Precisa de pelo menos 1 categoria")
    private List<CategoryRequestDTO> categories = new ArrayList<>();

    public ProductRequestDTO(){

    }

    public ProductRequestDTO(String name, String description, Double price, String imgUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductRequestDTO(Product product){
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();
        imgUrl = product.getImgUrl();
        for(Category category : product.getCategories()){
            categories.add(new CategoryRequestDTO(category));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<CategoryRequestDTO> getCategories() {
        return categories;
    }
}
