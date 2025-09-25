package com.estudo.dscommerce.services.impl;

import com.estudo.dscommerce.dto.response.ProductResponseDTO;
import com.estudo.dscommerce.exception.exceptions.ResourceNotFoundException;
import com.estudo.dscommerce.model.Product;
import com.estudo.dscommerce.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private Product product;

    @BeforeEach
    void setUp() throws Exception{

        existingId = 1L;
        nonExistingId = 2L;
        product = new Product();
        product.setId(existingId);
        product.setName("Smart Tv");

        Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));

        Mockito.when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        
    }

    @Test
    public void findByIdShouldReturnProductResponseDTOWhenIdExists(){

        ProductResponseDTO result = productService.findById(existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingId, result.getId());
        Assertions.assertEquals(product.getName(), result.getName());

        Mockito.verify(productRepository, Mockito.times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist(){

        Assertions.assertThrows(ResourceNotFoundException.class,() ->{
            productService.findById(nonExistingId);
        });

        Mockito.verify(productRepository, Mockito.times(1)).findById(nonExistingId);
    }
}
