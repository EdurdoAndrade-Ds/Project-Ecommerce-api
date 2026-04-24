package org.ecommerce.ecommerceapi.modules.payment.service;

import org.ecommerce.ecommerceapi.exceptions.BusinessException;
import org.ecommerce.ecommerceapi.exceptions.DuplicatePaymentException;
import org.ecommerce.ecommerceapi.exceptions.ForbiddenAccessException;
import org.ecommerce.ecommerceapi.exceptions.ResourceNotFoundException;
import org.ecommerce.ecommerceapi.modules.order.entity.Order;
import org.ecommerce.ecommerceapi.modules.order.repository.OrderStatus;
import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentRequestDTO;
import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentResponseDTO;
import org.ecommerce.ecommerceapi.modules.payment.entity.Payment;
import org.ecommerce.ecommerceapi.modules.payment.repository.PaymentRepository;
import org.ecommerce.ecommerceapi.modules.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public PaymentResponseDTO pay(PaymentRequestDTO dto, Long clienteId) {
        Order order = orderRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if (!order.getCliente().getId().equals(clienteId)) {
            throw new ForbiddenAccessException("Acesso negado ao pedido");
        }
        if (order.isCancelado()) {
            throw new BusinessException("Pedido cancelado");
        }
        if (dto.getPrice() == null || dto.getPrice().compareTo(order.getTotal()) != 0) {
            throw new BusinessException("Valor do pagamento inválido");
        }

        List<Payment> existingPayments = paymentRepository.findByOrderId(dto.getPedidoId());
        if (!existingPayments.isEmpty()) {
            throw new DuplicatePaymentException("Este pedido já foi pago");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPrice(dto.getPrice());
        payment.setDatePayment(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);

        order.setStatus(OrderStatus.PAGO);
        orderRepository.save(order);

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
