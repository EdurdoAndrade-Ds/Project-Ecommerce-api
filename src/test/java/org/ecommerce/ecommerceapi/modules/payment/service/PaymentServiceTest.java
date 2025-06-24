package org.ecommerce.ecommerceapi.modules.payment.service;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentRequestDTO;
import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentResponseDTO;
import org.ecommerce.ecommerceapi.modules.payment.entity.Payment;
import org.ecommerce.ecommerceapi.modules.payment.repository.PaymentRepository;
import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.pedido.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Pedido pedido;
    private PaymentRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(1L);

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.TEN);
        pedido.setCancelado(false);

        requestDTO = new PaymentRequestDTO();
        requestDTO.setPedidoId(1L);
        requestDTO.setValor(BigDecimal.TEN);
    }

    @Test
    void testPagarSucesso() {
        Payment payment = new Payment();
        payment.setId(2L);
        payment.setPedido(pedido);
        payment.setValor(BigDecimal.TEN);
        payment.setDataPagamento(LocalDateTime.now());

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentResponseDTO response = paymentService.pagar(requestDTO, 1L);

        assertNotNull(response);
        assertEquals(2L, response.getId());
        assertEquals(1L, response.getPedidoId());
        assertEquals(BigDecimal.TEN, response.getValor());
    }

    @Test
    void testPagarPedidoNaoEncontrado() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> paymentService.pagar(requestDTO, 1L));
    }

    @Test
    void testPagarClienteInvalido() {
        ClienteEntity outro = new ClienteEntity();
        outro.setId(2L);
        pedido.setCliente(outro);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        assertThrows(RuntimeException.class, () -> paymentService.pagar(requestDTO, 1L));
    }

    @Test
    void testPagarPedidoCancelado() {
        pedido.setCancelado(true);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        assertThrows(RuntimeException.class, () -> paymentService.pagar(requestDTO, 1L));
    }

    @Test
    void testPagarValorInvalido() {
        requestDTO.setValor(BigDecimal.ONE);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        assertThrows(RuntimeException.class, () -> paymentService.pagar(requestDTO, 1L));
    }
}
