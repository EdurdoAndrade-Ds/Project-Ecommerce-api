package org.ecommerce.ecommerceapi.pagamento.service;

import org.ecommerce.ecommerceapi.order.model.Pedido;
import org.ecommerce.ecommerceapi.order.repository.PedidoRepository;
import org.ecommerce.ecommerceapi.pagamento.dto.PagamentoRequestDTO;
import org.ecommerce.ecommerceapi.pagamento.dto.PagamentoResponseDTO;
import org.ecommerce.ecommerceapi.pagamento.model.Pagamento;
import org.ecommerce.ecommerceapi.pagamento.model.StatusPagamento;
import org.ecommerce.ecommerceapi.pagamento.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public PagamentoResponseDTO processarPagamento(PagamentoRequestDTO dto, String email) {
    // TODO: implementar lógica
    return new PagamentoResponseDTO();
}
}