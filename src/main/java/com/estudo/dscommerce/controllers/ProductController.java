package com.estudo.dscommerce.controllers;

import com.estudo.dscommerce.dto.response.ProductResponseDTO;
import com.estudo.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id){
        ProductResponseDTO product = productService.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findAll(Pageable pageable){
        Page<ProductResponseDTO> productResponseDTOS = productService.findAll(pageable);
        return ResponseEntity.ok().body(productResponseDTOS);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> insert(@RequestBody ProductResponseDTO productResponseDTO){
        productResponseDTO = productService.insert(productResponseDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productResponseDTO.getId()).toUri();
        return ResponseEntity.created(location).body(productResponseDTO);
    }

}
