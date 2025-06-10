package org.ecommerce.ecommerceapi.modules.pedido.repository;

import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    Optional<Pedido> findByIdAndClienteId(Long id, Long clienteId);
    List<Pedido> findByClienteIdAndCanceladoTrue(Long clienteId);
}
