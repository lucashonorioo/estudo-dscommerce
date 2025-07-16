package com.estudo.dscommerce.repositories;

import com.estudo.dscommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
