package org.ecommerce.ecommerceapi.order.repository;

import org.ecommerce.ecommerceapi.order.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}