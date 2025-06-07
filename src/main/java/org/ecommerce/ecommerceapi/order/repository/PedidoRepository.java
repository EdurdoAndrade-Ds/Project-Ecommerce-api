package org.ecommerce.ecommerceapi.order.repository;

import org.ecommerce.ecommerceapi.order.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}