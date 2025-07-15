package com.estudo.dscommerce.controllers;

import com.estudo.dscommerce.dto.response.OrderResponseDTO;
import com.estudo.dscommerce.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id){
        OrderResponseDTO orderResponseDTO = orderService.findById(id);
        return ResponseEntity.ok().body(orderResponseDTO);
    }
}
