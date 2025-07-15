package com.estudo.dscommerce.services.impl;

import com.estudo.dscommerce.dto.response.OrderResponseDTO;
import com.estudo.dscommerce.exception.exceptions.ResourceNotFoundException;
import com.estudo.dscommerce.model.Order;
import com.estudo.dscommerce.repositories.OrderRepository;
import com.estudo.dscommerce.services.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new OrderResponseDTO(order);
    }
}
