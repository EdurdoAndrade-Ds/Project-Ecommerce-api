package org.ecommerce.ecommerceapi.modules.pagamento.repository;

import org.ecommerce.ecommerceapi.modules.pagamento.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    List<Pagamento> findByPedidoClienteId(Long clienteId);
    List<Pagamento> findAllByPedidoClienteId(Long clienteId);
    Optional<Pagamento> findByIdAndPedidoClienteId(Long pagamentoId, Long clienteId);
}
