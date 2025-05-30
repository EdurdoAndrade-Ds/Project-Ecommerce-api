package org.ecommerce.ecommerceapi.order.repository;

import org.ecommerce.ecommerceapi.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<org.ecommerce.ecommerceapi.order.model.Order, Long> {
}