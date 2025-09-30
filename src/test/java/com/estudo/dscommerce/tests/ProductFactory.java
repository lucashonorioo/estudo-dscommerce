package com.estudo.dscommerce.tests;

import com.estudo.dscommerce.dto.request.CategoryRequestDTO;
import com.estudo.dscommerce.dto.request.ProductRequestDTO;
import com.estudo.dscommerce.model.Category;
import com.estudo.dscommerce.model.Product;

public class ProductFactory {

    public static Product createProduct(){
        Category category = CategoryFactory.createCategory();
        Product product = new Product(1L, "Smart Tv", "Televisão ruim", 1000.0, "http://casasdebaiano.com/tv-ruim");
        product.getCategories().add(category);
        return product;
    }

    public static Product createProduct(String name){
        Product product = createProduct();
        product.setName(name);
        return product;
    }

    public static ProductRequestDTO createProductRequestDto(){
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        Product product = createProduct();

        requestDTO.setName(product.getName());
        requestDTO.setDescription(product.getDescription());
        requestDTO.setPrice(product.getPrice());
        requestDTO.setImgUrl(product.getImgUrl());

        requestDTO.getCategories().add(new CategoryRequestDTO(1L,"Eletrônicos"));
        return requestDTO;
    }

}
