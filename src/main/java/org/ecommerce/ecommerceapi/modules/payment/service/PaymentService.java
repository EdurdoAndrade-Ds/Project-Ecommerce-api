package org.ecommerce.ecommerceapi.modules.payment.service;

import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentRequestDTO;
import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentResponseDTO;
import org.ecommerce.ecommerceapi.modules.payment.entity.Payment;
import org.ecommerce.ecommerceapi.modules.payment.repository.PaymentRepository;
import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public PaymentResponseDTO pagar(PaymentRequestDTO dto, Long clienteId) {
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (!pedido.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("Acesso negado ao pedido");
        }
        if (pedido.isCancelado()) {
            throw new RuntimeException("Pedido cancelado");
        }
        if (dto.getValor() == null || dto.getValor().compareTo(pedido.getTotal()) != 0) {
            throw new RuntimeException("Valor do pagamento inválido");
        }

        Payment payment = new Payment();
        payment.setPedido(pedido);
        payment.setValor(dto.getValor());
        payment.setDataPagamento(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);
        return mapToDTO(saved);
    }

    private PaymentResponseDTO mapToDTO(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(payment.getId());
        dto.setPedidoId(payment.getPedido().getId());
        dto.setValor(payment.getValor());
        dto.setDataPagamento(payment.getDataPagamento());
        return dto;
    }
}
