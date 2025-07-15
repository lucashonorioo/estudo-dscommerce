package com.estudo.dscommerce.repositories;

import com.estudo.dscommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
