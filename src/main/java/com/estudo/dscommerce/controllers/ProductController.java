package com.estudo.dscommerce.controllers;

import com.estudo.dscommerce.dto.request.ProductRequestDTO;
import com.estudo.dscommerce.dto.response.ProductResponseDTO;
import com.estudo.dscommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id){
        ProductResponseDTO product = productService.findById(id);
        return ResponseEntity.ok().body(product);
    }


    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findAll(@RequestParam(name = "name", defaultValue = "")String name, Pageable pageable){
        Page<ProductResponseDTO> productResponseDTOS = productService.findAll(name, pageable);
        return ResponseEntity.ok().body(productResponseDTOS);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> insert(@Valid @RequestBody ProductRequestDTO productRequestDTO){
        ProductResponseDTO productResponseDTO = productService.insert(productRequestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productResponseDTO.getId()).toUri();
        return ResponseEntity.created(location).body(productResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id,@Valid @RequestBody ProductRequestDTO productRequestDTO){
        ProductResponseDTO productResponseDTO = productService.update(id, productRequestDTO);
        return ResponseEntity.ok(productResponseDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
