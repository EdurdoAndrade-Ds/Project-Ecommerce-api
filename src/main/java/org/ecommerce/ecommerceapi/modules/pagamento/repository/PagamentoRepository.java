package org.ecommerce.ecommerceapi.modules.pagamento.repository;

import org.ecommerce.ecommerceapi.modules.pagamento.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    Optional<Pagamento> findByPedidoId(Long pedidoId);
}