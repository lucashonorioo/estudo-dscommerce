package com.estudo.dscommerce.services.impl;

import com.estudo.dscommerce.dto.request.ProductRequestDTO;
import com.estudo.dscommerce.dto.response.ProductResponseDTO;
import com.estudo.dscommerce.dto.response.ProductResponseMinDTO;
import com.estudo.dscommerce.exception.exceptions.DatabaseException;
import com.estudo.dscommerce.exception.exceptions.ResourceNotFoundException;
import com.estudo.dscommerce.model.Product;
import com.estudo.dscommerce.repositories.ProductRepository;
import com.estudo.dscommerce.tests.ProductFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTests {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private Product product;
    private String productName;
    Pageable pageable;
    Page<Product>  productPage;
    ProductRequestDTO requestDTO;
    Product existingProduct;

    @BeforeEach
    void setUp() throws Exception{

        existingId = 1L;
        nonExistingId = 2L;

        productName = "Smart Tv";
        pageable = PageRequest.of(0, 10);

        product = ProductFactory.createProduct(productName);

        requestDTO = ProductFactory.createProductRequestDto();

        existingProduct = ProductFactory.createProduct();

    }

    @Test
    public void findByIdShouldReturnProductResponseDTOWhenIdExists(){

        Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));

        ProductResponseDTO result = productService.findById(existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingId, result.getId());
        Assertions.assertEquals(product.getName(), result.getName());

        Mockito.verify(productRepository, Mockito.times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist(){

        Mockito.when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,() ->{
            productService.findById(nonExistingId);
        });

        Mockito.verify(productRepository, Mockito.times(1)).findById(nonExistingId);
    }

    @Test
    public void findAllShouldReturnPageOfProductResponseMinDTO(){

        productPage = new PageImpl<>(List.of(product), pageable, 1);
        Mockito.when(productRepository.searchByName(any(), any())).thenReturn(productPage);

        Page<ProductResponseMinDTO> result = productService.findAll(productName, pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getNumberOfElements());
        Assertions.assertEquals(productName, result.getContent().get(0).getName());

        Mockito.verify(productRepository).searchByName(productName, pageable);
    }

    @Test
    public void insertShouldReturnProductResponseDTOSaved(){

        ProductRequestDTO requestDTO = ProductFactory.createProductRequestDto();

        Mockito.when(productRepository.save(any())).thenReturn(product);

        ProductResponseDTO result = productService.insert(requestDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(product.getId(), result.getId());
        Assertions.assertEquals(requestDTO.getName(), result.getName());

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));

    }

    @Test
    public void updateShouldReturnProductResponseDtoWhenIdExists(){


        Mockito.when(productRepository.getReferenceById(existingId)).thenReturn(existingProduct);

        Mockito.when(productRepository.save(any())).thenReturn(existingProduct);

        ProductResponseDTO result = productService.update(existingId, requestDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingId, result.getId());

        Assertions.assertEquals(requestDTO.getName(), result.getName());


    }

    @Test
    public void updateShouldReturnResourceNotFoundExceptionWhenDoesIdNotExists(){

        Mockito.when(productRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.update(nonExistingId, requestDTO);
        });

        Mockito.verify(productRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists(){

        Mockito.doNothing().when(productRepository).deleteById(existingId);

        Assertions.assertDoesNotThrow( () ->{
            productService.delete(existingId);
        });

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldReturnResourceNotFoundExceptionWhenDoesIdNotExists(){

        Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingId);

        Assertions.assertThrows(ResourceNotFoundException.class, () ->{
           productService.delete(nonExistingId);
        });
    }

    @Test
    public void deleteShouldReturnDatabaseExceptionWhenIdIsDependent(){

        long dependentId = 3L;

        Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);

        Assertions.assertThrows(DatabaseException.class, ()->{
            productService.delete(dependentId);
        });

        Mockito.verify(productRepository).deleteById(dependentId);
    }

}
