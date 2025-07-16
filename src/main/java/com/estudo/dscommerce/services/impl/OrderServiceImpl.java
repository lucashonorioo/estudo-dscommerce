package com.estudo.dscommerce.services.impl;

import com.estudo.dscommerce.dto.request.ItemsRequestDTO;
import com.estudo.dscommerce.dto.request.OrderRequestDTO;
import com.estudo.dscommerce.dto.response.OrderResponseDTO;
import com.estudo.dscommerce.exception.exceptions.ResourceNotFoundException;
import com.estudo.dscommerce.model.*;
import com.estudo.dscommerce.repositories.OrderItemRepository;
import com.estudo.dscommerce.repositories.OrderRepository;
import com.estudo.dscommerce.repositories.ProductRepository;
import com.estudo.dscommerce.services.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserServiceImpl userService;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;


    public OrderServiceImpl(OrderRepository orderRepository, UserServiceImpl userService, ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new OrderResponseDTO(order);
    }

    @Transactional
    public OrderResponseDTO insert(OrderRequestDTO orderRequestDTO){
        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated();
        order.setClient(user);
        for(ItemsRequestDTO itemsRequestDTO : orderRequestDTO.getItems()){
            Product product = productRepository.getReferenceById(itemsRequestDTO.getProductId());
            OrderItem orderItem = new OrderItem(order, product, itemsRequestDTO.getQuantity(), product.getPrice());
            order.getItems().add(orderItem);
        }

        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderResponseDTO(order);
    }
}
