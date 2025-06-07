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

    public PagamentoResponseDTO processarPagamento(PagamentoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setMetodo(dto.getMetodo());
        pagamento.setStatus(StatusPagamento.APROVADO);
        pagamento.setDataPagamento(LocalDateTime.now());

        pagamento = pagamentoRepository.save(pagamento);

        PagamentoResponseDTO response = new PagamentoResponseDTO();
        response.setId(pagamento.getId());
        response.setPedidoId(pedido.getId());
        response.setMetodo(pagamento.getMetodo());
        response.setStatus(pagamento.getStatus().name());
        response.setDataPagamento(pagamento.getDataPagamento());

        return response;
    }
}
