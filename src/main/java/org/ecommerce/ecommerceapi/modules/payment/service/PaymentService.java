package org.ecommerce.ecommerceapi.modules.payment.service;

import org.ecommerce.ecommerceapi.modules.order.entity.Order;
import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentRequestDTO;
import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentResponseDTO;
import org.ecommerce.ecommerceapi.modules.payment.entity.Payment;
import org.ecommerce.ecommerceapi.modules.payment.repository.PaymentRepository;
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
        Order order = orderRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (!order.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("Acesso negado ao pedido");
        }
        if (order.isCancelado()) {
            throw new RuntimeException("Pedido cancelado");
        }
        if (dto.getPrice() == null || dto.getPrice().compareTo(order.getTotal()) != 0) {
            throw new RuntimeException("Valor do pagamento inválido");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPrice(dto.getPrice());
        payment.setDatePayment(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);
        return mapToDTO(saved, order);
    }

    private PaymentResponseDTO mapToDTO(Payment payment, Order order) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(payment.getId());
        dto.setPedidoId(payment.getOrder().getId());
        dto.setValor(payment.getPrice());
        dto.setDataPagamento(payment.getDatePayment());
        dto.setClienteId(order.getCliente().getId());
        return dto;
    }
}
