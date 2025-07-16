package com.estudo.dscommerce.controllers;

import com.estudo.dscommerce.dto.request.OrderRequestDTO;
import com.estudo.dscommerce.dto.response.OrderResponseDTO;
import com.estudo.dscommerce.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<OrderResponseDTO> insert(@Valid @RequestBody OrderRequestDTO orderRequestDTO){
        OrderResponseDTO orderResponseDTO = orderService.insert(orderRequestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderResponseDTO.getId()).toUri();

        return ResponseEntity.created(location).body(orderResponseDTO);
    }
}
