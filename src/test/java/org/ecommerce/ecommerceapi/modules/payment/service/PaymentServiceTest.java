package org.ecommerce.ecommerceapi.modules.payment.service;

import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentRequestDTO;
import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentResponseDTO;
import org.ecommerce.ecommerceapi.modules.payment.entity.Payment;
import org.ecommerce.ecommerceapi.modules.payment.repository.PaymentRepository;
import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.pedido.repository.PedidoRepository;
import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(1L);

        pedido = new Pedido();
        pedido.setId(2L);
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.valueOf(100));
    }

    @Test
    void pagarComSucesso() {
        PaymentRequestDTO dto = new PaymentRequestDTO();
        dto.setPedidoId(2L);
        dto.setValor(BigDecimal.valueOf(100));

        when(pedidoRepository.findById(2L)).thenReturn(Optional.of(pedido));
        Payment saved = new Payment();
        saved.setId(10L);
        saved.setPedido(pedido);
        saved.setValor(dto.getValor());
        saved.setDataPagamento(LocalDateTime.now());
        when(paymentRepository.save(any(Payment.class))).thenReturn(saved);

        PaymentResponseDTO response = paymentService.pagar(dto, 1L);

        assertNotNull(response);
        assertEquals(saved.getId(), response.getId());
        assertEquals(dto.getValor(), response.getValor());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void pagarValorInvalido() {
        PaymentRequestDTO dto = new PaymentRequestDTO();
        dto.setPedidoId(2L);
        dto.setValor(BigDecimal.valueOf(50));

        when(pedidoRepository.findById(2L)).thenReturn(Optional.of(pedido));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> paymentService.pagar(dto, 1L));

        assertEquals("Valor do pagamento inválido", ex.getMessage());
        verify(paymentRepository, never()).save(any());
    }
}
