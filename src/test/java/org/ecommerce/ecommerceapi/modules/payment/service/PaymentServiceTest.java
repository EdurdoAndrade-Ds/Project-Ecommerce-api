package org.ecommerce.ecommerceapi.modules.payment.service;




import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;


import org.ecommerce.ecommerceapi.modules.order.entity.Order;
import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentRequestDTO;
import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentResponseDTO;
import org.ecommerce.ecommerceapi.modules.payment.entity.Payment;
import org.ecommerce.ecommerceapi.modules.payment.repository.PaymentRepository;
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

    private Order order;
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

        order = new Order();

        order.setId(1L);
        order.setCliente(cliente);
        order.setTotal(BigDecimal.valueOf(100));

        requestDTO = new PaymentRequestDTO();
        requestDTO.setPedidoId(1L);
        requestDTO.setPrice(BigDecimal.valueOf(100));
    }

    @Test
    void pagarComSucesso() {
        PaymentRequestDTO dto = new PaymentRequestDTO();
        dto.setPedidoId(2L);
        dto.setPrice(BigDecimal.valueOf(100));

        when(orderRepository.findById(2L)).thenReturn(Optional.of(order));
        Payment saved = new Payment();
        saved.setId(10L);
        saved.setOrder(order);
        saved.setPrice(dto.getPrice());
        saved.setDatePayment(LocalDateTime.now());
        when(paymentRepository.save(any(Payment.class))).thenReturn(saved);

        PaymentResponseDTO response = paymentService.pay(dto, 1L);

        assertNotNull(response);
        assertEquals(saved.getId(), response.getId());
        assertEquals(dto.getPrice(), response.getValor());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void pagarValorInvalido() {
        PaymentRequestDTO dto = new PaymentRequestDTO();
        dto.setPedidoId(2L);
        dto.setPrice(BigDecimal.valueOf(50));

        when(orderRepository.findById(2L)).thenReturn(Optional.of(order));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> paymentService.pay(dto, 1L));

        assertEquals("Valor do pagamento inválido", ex.getMessage());
        verify(paymentRepository, never()).save(any());


        order.setId(1L);
        order.setCliente(cliente);
        order.setTotal(BigDecimal.TEN);
        order.setCancelado(false);

        requestDTO = new PaymentRequestDTO();
        requestDTO.setPedidoId(1L);
        requestDTO.setPrice(BigDecimal.TEN);
    }

    @Test
    void testPagarSucesso() {
        Payment payment = new Payment();
        payment.setId(2L);
        payment.setOrder(order);
        payment.setPrice(BigDecimal.TEN);
        payment.setDatePayment(LocalDateTime.now());

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentResponseDTO response = paymentService.pay(requestDTO, 1L);

        assertNotNull(response);
        assertEquals(2L, response.getId());
        assertEquals(1L, response.getPedidoId());
        assertEquals(BigDecimal.TEN, response.getValor());
    }

    @Test
    void testPagarPedidoNaoEncontrado() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> paymentService.pay(requestDTO, 1L));
    }

    @Test
    void testPagarClienteInvalido() {
        ClientEntity outro = new ClientEntity();
        outro.setId(2L);
        order.setCliente(outro);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(RuntimeException.class, () -> paymentService.pay(requestDTO, 1L));
    }

    @Test
    void testPagarPedidoCancelado() {
        order.setCancelado(true);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(RuntimeException.class, () -> paymentService.pay(requestDTO, 1L));
    }

    @Test
    void testPagarValorInvalido() {
        requestDTO.setPrice(BigDecimal.ONE);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(RuntimeException.class, () -> paymentService.pay(requestDTO, 1L));

    }
}
