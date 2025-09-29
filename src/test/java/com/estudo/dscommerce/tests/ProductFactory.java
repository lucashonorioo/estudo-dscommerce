package com.estudo.dscommerce.tests;

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

}
