package org.ecommerce.ecommerceapi.pagamento.repository;

import org.ecommerce.ecommerceapi.pagamento.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
