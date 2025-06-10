package org.ecommerce.ecommerceapi.modules.pagamento.service;

import lombok.Data;
import org.ecommerce.ecommerceapi.modules.pagamento.dto.PagamentoRequestDTO;
import org.ecommerce.ecommerceapi.modules.pagamento.dto.PagamentoResponseDTO;
import org.ecommerce.ecommerceapi.modules.pagamento.entity.Pagamento;
import org.ecommerce.ecommerceapi.modules.pagamento.repository.PagamentoRepository;
import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Data
@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public PagamentoResponseDTO criar(PagamentoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setValor(dto.getValor());
        pagamento.setStatus("PENDENTE");
        pagamento.setData(LocalDateTime.now());

        pagamento = pagamentoRepository.save(pagamento);

        PagamentoResponseDTO response = new PagamentoResponseDTO();
        response.setId(pagamento.getId());
        response.setPedidoId(pedido.getId());
        response.setValor(pagamento.getValor());
        response.setStatus(pagamento.getStatus());
        response.setData(pagamento.getData());

        return response;
    }
}