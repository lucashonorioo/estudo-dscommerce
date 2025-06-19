package com.estudo.dscommerce.services;

import com.estudo.dscommerce.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductResponseDTO findById(Long id);
    Page<ProductResponseDTO> findAll(Pageable pageable);


}
