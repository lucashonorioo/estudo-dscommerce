package com.estudo.dscommerce.services;

import com.estudo.dscommerce.dto.response.ProductResponseDTO;

public interface ProductService {

    public ProductResponseDTO findById(Long id);

}
