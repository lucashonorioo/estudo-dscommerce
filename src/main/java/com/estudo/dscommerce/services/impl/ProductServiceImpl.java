package com.estudo.dscommerce.services.impl;

import com.estudo.dscommerce.dto.response.ProductResponseDTO;
import com.estudo.dscommerce.exceptions.ResourceNotFoundException;
import com.estudo.dscommerce.model.Product;
import com.estudo.dscommerce.repositories.ProductRepository;
import com.estudo.dscommerce.services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    @Transactional
    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new ProductResponseDTO(product);

    }

    @Override
    @Transactional
    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map( p -> new ProductResponseDTO(p));
    }

    @Override
    @Transactional
    public ProductResponseDTO insert(ProductResponseDTO productResponseDTO){
        Product product = new Product();
        copyDtoToEntity(productResponseDTO , product);

        product = productRepository.save(product);

        return new ProductResponseDTO(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO update(Long id, ProductResponseDTO productResponseDTO){
        Product product = productRepository.getReferenceById(id);
        copyDtoToEntity(productResponseDTO, product);
        product = productRepository.save(product);

        return new ProductResponseDTO(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private static void copyDtoToEntity(ProductResponseDTO productResponseDTO, Product product){
        product.setName(productResponseDTO.getName());
        product.setDescription(productResponseDTO.getDescription());
        product.setPrice(productResponseDTO.getPrice());
        product.setImgUrl(productResponseDTO.getImgUrl());
    }

}
