package org.ecommerce.ecommerceapi.Pedido.repository;

import org.ecommerce.ecommerceapi.Pedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoService extends JpaRepository<org.ecommerce.ecommerceapi.pedido.model.pedido, Long> {
}