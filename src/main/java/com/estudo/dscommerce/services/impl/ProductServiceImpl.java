package com.estudo.dscommerce.services.impl;

import com.estudo.dscommerce.dto.request.CategoryRequestDTO;
import com.estudo.dscommerce.dto.request.ProductRequestDTO;
import com.estudo.dscommerce.dto.response.CategoryResponseDTO;
import com.estudo.dscommerce.dto.response.ProductResponseDTO;
import com.estudo.dscommerce.dto.response.ProductResponseMinDTO;
import com.estudo.dscommerce.exception.exceptions.DatabaseException;
import com.estudo.dscommerce.exception.exceptions.ResourceNotFoundException;
import com.estudo.dscommerce.model.Category;
import com.estudo.dscommerce.model.Product;
import com.estudo.dscommerce.repositories.ProductRepository;
import com.estudo.dscommerce.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new ProductResponseDTO(product);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseMinDTO> findAll(String name, Pageable pageable) {
        Page<Product> products = productRepository.searchByName(name, pageable);
        return products.map( p -> new ProductResponseMinDTO(p));
    }

    @Override
    @Transactional
    public ProductResponseDTO insert(ProductRequestDTO productRequestDTO){
        Product product = new Product();
        copyDtoToEntity(productRequestDTO , product);

        product = productRepository.save(product);

        return new ProductResponseDTO(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO){
            try{
                Product product = productRepository.getReferenceById(id);
                copyDtoToEntity(productRequestDTO, product);
                product = productRepository.save(product);
                return new ProductResponseDTO(product);
            }
            catch (EntityNotFoundException e){
                throw new ResourceNotFoundException("Recurso não encontrado");
            }

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        try{
            productRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private static void copyDtoToEntity(ProductRequestDTO productRequestDTO, Product product){
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setImgUrl(productRequestDTO.getImgUrl());

        product.getCategories().clear();
        for(CategoryRequestDTO categoryRequestDTO : productRequestDTO.getCategories()){
            Category category = new Category();
            category.setId(categoryRequestDTO.getId());
            product.getCategories().add(category);
        }

    }

}
