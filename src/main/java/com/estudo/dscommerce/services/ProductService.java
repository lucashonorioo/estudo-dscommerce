package com.estudo.dscommerce.services;

import com.estudo.dscommerce.dto.request.ProductRequestDTO;
import com.estudo.dscommerce.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductResponseDTO findById(Long id);
    Page<ProductResponseDTO> findAll(String name, Pageable pageable);
    ProductResponseDTO insert(ProductRequestDTO productRequestDTO);
    ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO);
    void delete(Long id);

}
