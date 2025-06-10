package org.ecommerce.ecommerceapi.modules.pagamento.service;

import lombok.Data;
import org.ecommerce.ecommerceapi.modules.pagamento.dto.PagamentoRequestDTO;
import org.ecommerce.ecommerceapi.modules.pagamento.dto.PagamentoResponseDTO;
import org.ecommerce.ecommerceapi.modules.pagamento.entity.Pagamento;
import org.ecommerce.ecommerceapi.modules.pagamento.repository.PagamentoRepository;
import org.ecommerce.ecommerceapi.modules.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public PagamentoResponseDTO criar(PagamentoRequestDTO dto, Long clienteId) {
        var pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (!pedido.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("Acesso negado ao pedido");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setValor(dto.getValor());
        pagamento.setStatus("PENDENTE");
        pagamento.setData(LocalDateTime.now());

        return mapToDTO(pagamentoRepository.save(pagamento));
    }

    public List<PagamentoResponseDTO> listar(Long clienteId) {
        return pagamentoRepository.findAllByPedidoClienteId(clienteId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PagamentoResponseDTO buscarPorId(Long id) {
        return pagamentoRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
    }

    public PagamentoResponseDTO atualizarStatus(Long id, String status) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        pagamento.setStatus(status);
        return mapToDTO(pagamentoRepository.save(pagamento));
    }

    private PagamentoResponseDTO mapToDTO(Pagamento pagamento) {
        PagamentoResponseDTO dto = new PagamentoResponseDTO();
        dto.setId(pagamento.getId());
        dto.setValor(pagamento.getValor());
        dto.setStatus(pagamento.getStatus());
        dto.setPedidoId(pagamento.getPedido().getId());
        dto.setData(pagamento.getData());
        return dto;
    }
}