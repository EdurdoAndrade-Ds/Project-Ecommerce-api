package org.ecommerce.ecommerceapi.modules.payment.controller;

import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentRequestDTO;
import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentResponseDTO;
import org.ecommerce.ecommerceapi.modules.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    @Mock
    private Authentication authentication;

    private PaymentRequestDTO requestDTO;
    private PaymentResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        requestDTO = new PaymentRequestDTO();
        requestDTO.setPedidoId(1L);
        requestDTO.setPrice(BigDecimal.TEN);

        responseDTO = new PaymentResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setPedidoId(1L);
        responseDTO.setValor(BigDecimal.TEN);
    }

    @Test
    void testPagar() {
        when(authentication.getName()).thenReturn("1");
        when(paymentService.pay(any(PaymentRequestDTO.class), eq(1L))).thenReturn(responseDTO);

        ResponseEntity<PaymentResponseDTO> response = paymentController.pay(requestDTO, authentication);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());

        ArgumentCaptor<PaymentRequestDTO> captor = ArgumentCaptor.forClass(PaymentRequestDTO.class);
        verify(paymentService, times(1)).pay(captor.capture(), eq(1L));
        assertEquals(requestDTO.getPedidoId(), captor.getValue().getPedidoId());
        assertEquals(requestDTO.getPrice(), captor.getValue().getPrice());
    }

    @Test
    void testPagar_ValorCorreto() {
        when(authentication.getName()).thenReturn("1");
        when(paymentService.pay(any(PaymentRequestDTO.class), eq(1L))).thenReturn(responseDTO);

        ResponseEntity<PaymentResponseDTO> response = paymentController.pay(requestDTO, authentication);

        BigDecimal valorEsperado = requestDTO.getPrice();
        BigDecimal valorRetornado = response.getBody().getValor();

        assertEquals(0, valorEsperado.compareTo(valorRetornado));
    }
}
