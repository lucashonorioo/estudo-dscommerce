package com.estudo.dscommerce.controllers;

import com.estudo.dscommerce.dto.response.ProductResponseDTO;
import com.estudo.dscommerce.model.Product;
import com.estudo.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ProductResponseDTO findById(@PathVariable Long id){
        ProductResponseDTO product = productService.findById(id);
        return product;
    }

}
