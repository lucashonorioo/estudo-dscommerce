package com.estudo.dscommerce.services.impl;

import com.estudo.dscommerce.dto.response.ProductResponseDTO;
import com.estudo.dscommerce.model.Product;
import com.estudo.dscommerce.repositories.ProductRepository;
import com.estudo.dscommerce.services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    @Transactional()
    public ProductResponseDTO findById(Long id) {
        Product product1 = productRepository.findById(id).get();
        return new ProductResponseDTO(product1);

    }
}
