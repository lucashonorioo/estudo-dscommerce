package com.estudo.dscommerce.services.impl;

import com.estudo.dscommerce.dto.request.ItemsRequestDTO;
import com.estudo.dscommerce.dto.request.OrderRequestDTO;
import com.estudo.dscommerce.dto.response.OrderResponseDTO;
import com.estudo.dscommerce.exception.exceptions.ForbiddenException;
import com.estudo.dscommerce.exception.exceptions.ResourceNotFoundException;
import com.estudo.dscommerce.model.Order;
import com.estudo.dscommerce.model.OrderItem;
import com.estudo.dscommerce.model.Product;
import com.estudo.dscommerce.model.User;
import com.estudo.dscommerce.repositories.OrderItemRepository;
import com.estudo.dscommerce.repositories.OrderRepository;
import com.estudo.dscommerce.repositories.ProductRepository;
import com.estudo.dscommerce.tests.OrderFactory;
import com.estudo.dscommerce.tests.ProductFactory;
import com.estudo.dscommerce.tests.UserFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTests {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AuthServiceImpl authService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private UserServiceImpl userService;

    private Long existingId, nonExistingId;
    private Long existingProductId, nonExistingProductId;

    private User client, admin;
    private Order order;
    private OrderResponseDTO orderResponseDTO;
    private OrderRequestDTO orderRequestDTO;
    private Product product;

    @BeforeEach
    private void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 3L;
        existingProductId = 1L;
        nonExistingProductId = 2L;

        admin = UserFactory.createCustomAdminUser(1L, "Jef");
        client = UserFactory.createCustomClientUser(2L, "Bob");

        order = OrderFactory.createOrder(client);


        orderResponseDTO =new OrderResponseDTO(order);

        orderRequestDTO = new OrderRequestDTO(order);

        product = ProductFactory.createProduct();



    }

    @Test
    public void findByIdShouldReturnOrderResponseDTOWhenIdExistsAndAdminLogged(){

        Mockito.when(orderRepository.findById(existingId)).thenReturn(Optional.of(order));

        Mockito.doNothing().when(authService).validateSelfOrAdmin(order.getClient().getId());

        OrderResponseDTO result = orderService.findById(existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingId);


    }

    @Test
    public void findByIdShouldReturnOrderResponseDTOWhenIdExistsAndSelfClientLogged(){

        Mockito.when(orderRepository.findById(existingId)).thenReturn(Optional.of(order));

        Mockito.doNothing().when(authService).validateSelfOrAdmin(order.getClient().getId());

        OrderResponseDTO result = orderService.findById(existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingId);



    }
    @Test
    public void findByIdShouldThrowForbiddenExceptionWhenUserIsNotAuthorized(){

        Mockito.when(orderRepository.findById(existingId)).thenReturn(Optional.of(order));

        Mockito.doThrow(ForbiddenException.class).when(authService).validateSelfOrAdmin(order.getClient().getId());

        Assertions.assertThrows(ForbiddenException.class, () -> {
           orderService.findById(existingId);
        });
    }

    @Test
    public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists(){

        Mockito.when(orderRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            orderService.findById(nonExistingId);
        });

    }

    @Test
    public void insertShouldReturnOrderDTOWhenAdminLogged(){

        Mockito.when(productRepository.getReferenceById(existingProductId)).thenReturn(product);

        Mockito.when(orderRepository.save(any())).thenReturn(order);

        Mockito.when(userService.authenticated()).thenReturn(admin);

        OrderResponseDTO result = orderService.insert(orderRequestDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingId);
    }

    @Test
    public void insertShouldReturnOrderDTOWhenClientLogged(){

        Mockito.when(productRepository.getReferenceById(existingProductId)).thenReturn(product);

        Mockito.when(orderRepository.save(any())).thenReturn(order);

        Mockito.when(userService.authenticated()).thenReturn(client);

        OrderResponseDTO result = orderService.insert(orderRequestDTO);

        Assertions.assertNotNull(result);
    }

    @Test
    public void insertShouldThrowsUsernameNotFoundExceptionWhenUserNotLogged(){

        Mockito.when(userService.authenticated()).thenThrow(UsernameNotFoundException.class);

        Assertions.assertThrows(UsernameNotFoundException.class, () ->{
           OrderResponseDTO result = orderService.insert(orderRequestDTO);
        });

        Mockito.verify(productRepository, Mockito.never()).getReferenceById(any());

    }

    @Test
    public void insertShouldThrowsEntityNotFoundExceptionWhenOrderProductIdDoesNotExist(){


        Mockito.when(userService.authenticated()).thenReturn(client);

        Mockito.when(productRepository.getReferenceById(nonExistingProductId)).thenThrow(EntityNotFoundException.class);

        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.getItems().add(new ItemsRequestDTO(nonExistingProductId, 1));

        Assertions.assertThrows(EntityNotFoundException.class, () ->{
           OrderResponseDTO result = orderService.insert(requestDTO);
        });

    }

}
