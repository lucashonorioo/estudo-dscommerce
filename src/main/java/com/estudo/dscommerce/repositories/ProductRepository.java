package com.estudo.dscommerce.repositories;

import com.estudo.dscommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
