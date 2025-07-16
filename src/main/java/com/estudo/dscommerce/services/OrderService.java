package com.estudo.dscommerce.services;

import com.estudo.dscommerce.dto.request.OrderRequestDTO;
import com.estudo.dscommerce.dto.response.OrderResponseDTO;

public interface OrderService {

    OrderResponseDTO findById(Long id);
    OrderResponseDTO insert(OrderRequestDTO orderRequestDTO);
}
