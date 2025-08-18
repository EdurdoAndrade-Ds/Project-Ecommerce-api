package org.ecommerce.ecommerceapi.modules.payment.service;

import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentRequestDTO;
import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentResponseDTO;
import org.ecommerce.ecommerceapi.modules.payment.entity.Payment;
import org.ecommerce.ecommerceapi.modules.payment.repository.PaymentRepository;
import org.ecommerce.ecommerceapi.modules.order.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    public PaymentResponseDTO pay(PaymentRequestDTO dto, Long clienteId) {
        Pedido pedido = orderRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (!pedido.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("Acesso negado ao pedido");
        }
        if (pedido.isCancelado()) {
            throw new RuntimeException("Pedido cancelado");
        }
        if (dto.getPrice() == null || dto.getPrice().compareTo(pedido.getTotal()) != 0) {
            throw new RuntimeException("Valor do pagamento inválido");
        }

        Payment payment = new Payment();
        payment.setPedido(pedido);
        payment.setPrice(dto.getPrice());
        payment.setDatePayment(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);
        return mapToDTO(saved, pedido);
    }

    private PaymentResponseDTO mapToDTO(Payment payment, Pedido pedido) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(payment.getId());
        dto.setPedidoId(payment.getPedido().getId());
        dto.setValor(payment.getPrice());
        dto.setDataPagamento(payment.getDatePayment());
        dto.setClienteId(pedido.getCliente().getId());
        return dto;
    }
}
