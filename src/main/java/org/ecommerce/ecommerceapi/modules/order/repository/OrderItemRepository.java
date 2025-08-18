package org.ecommerce.ecommerceapi.modules.order.repository;

import org.ecommerce.ecommerceapi.modules.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}