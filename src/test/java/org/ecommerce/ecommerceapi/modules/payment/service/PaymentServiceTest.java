package org.ecommerce.ecommerceapi.modules.payment.service;




import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;


import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentRequestDTO;
import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentResponseDTO;
import org.ecommerce.ecommerceapi.modules.payment.entity.Payment;
import org.ecommerce.ecommerceapi.modules.payment.repository.PaymentRepository;
import org.ecommerce.ecommerceapi.modules.order.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.order.repository.OrderRepository;


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
    private OrderRepository orderRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Pedido pedido;
    private PaymentRequestDTO requestDTO;
    private ClientEntity cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new ClientEntity();
        cliente.setId(1L);
        cliente.setName("Teste");
        cliente.setEmail("teste@email.com");
        // preencha outros campos obrigatórios se necessário

        pedido = new Pedido();

        pedido.setId(1L);
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.valueOf(100));

        requestDTO = new PaymentRequestDTO();
        requestDTO.setPedidoId(1L);
        requestDTO.setValor(BigDecimal.valueOf(100));
    }

    @Test
    void pagarComSucesso() {
        PaymentRequestDTO dto = new PaymentRequestDTO();
        dto.setPedidoId(2L);
        dto.setValor(BigDecimal.valueOf(100));

        when(orderRepository.findById(2L)).thenReturn(Optional.of(pedido));
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

        when(orderRepository.findById(2L)).thenReturn(Optional.of(pedido));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> paymentService.pagar(dto, 1L));

        assertEquals("Valor do pagamento inválido", ex.getMessage());
        verify(paymentRepository, never()).save(any());


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

        when(orderRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentResponseDTO response = paymentService.pagar(requestDTO, 1L);

        assertNotNull(response);
        assertEquals(2L, response.getId());
        assertEquals(1L, response.getPedidoId());
        assertEquals(BigDecimal.TEN, response.getValor());
    }

    @Test
    void testPagarPedidoNaoEncontrado() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> paymentService.pagar(requestDTO, 1L));
    }

    @Test
    void testPagarClienteInvalido() {
        ClientEntity outro = new ClientEntity();
        outro.setId(2L);
        pedido.setCliente(outro);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(pedido));

        assertThrows(RuntimeException.class, () -> paymentService.pagar(requestDTO, 1L));
    }

    @Test
    void testPagarPedidoCancelado() {
        pedido.setCancelado(true);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(pedido));

        assertThrows(RuntimeException.class, () -> paymentService.pagar(requestDTO, 1L));
    }

    @Test
    void testPagarValorInvalido() {
        requestDTO.setValor(BigDecimal.ONE);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(pedido));

        assertThrows(RuntimeException.class, () -> paymentService.pagar(requestDTO, 1L));

    }
}
