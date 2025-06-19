package com.estudo.dscommerce.controllers;

import com.estudo.dscommerce.dto.response.ProductResponseDTO;
import com.estudo.dscommerce.model.Product;
import com.estudo.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public Page<ProductResponseDTO> findAll(Pageable pageable){
        return productService.findAll(pageable);
    }

    @PostMapping
    public ProductResponseDTO insert(@RequestBody ProductResponseDTO productResponseDTO){
        return productService.insert(productResponseDTO);
    }

}
