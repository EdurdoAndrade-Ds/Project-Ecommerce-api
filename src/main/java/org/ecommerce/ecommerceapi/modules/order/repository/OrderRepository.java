package org.ecommerce.ecommerceapi.modules.order.repository;

import org.ecommerce.ecommerceapi.modules.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClienteId(Long clienteId);
    Optional<Order> findByIdAndClienteId(Long id, Long clienteId);
    List<Order> findByClienteIdAndCanceladoTrue(Long clienteId);
}