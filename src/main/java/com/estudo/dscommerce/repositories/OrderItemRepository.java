package com.estudo.dscommerce.repositories;

import com.estudo.dscommerce.model.OrderItem;
import com.estudo.dscommerce.model.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
