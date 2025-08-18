package org.ecommerce.ecommerceapi.modules.order.controller;

import org.ecommerce.ecommerceapi.modules.order.dto.OrderRequestDTO;
import org.ecommerce.ecommerceapi.modules.order.dto.OrderResponseDTO;
import org.ecommerce.ecommerceapi.modules.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private Authentication authentication;

    private OrderRequestDTO orderRequestDTO;
    private OrderResponseDTO orderResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderRequestDTO = new OrderRequestDTO();
        // Configure pedidoRequestDTO conforme necessário

        orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(1L);
        // Configure pedidoResponseDTO conforme necessário
    }

    @Test
    void testCriar() {
        // Arrange
        when(authentication.getName()).thenReturn("1"); // Simula o ID do cliente
        when(orderService.create(any(OrderRequestDTO.class), eq(1L))).thenReturn(orderResponseDTO);

        // Act
        ResponseEntity<OrderResponseDTO> response = orderController.create(orderRequestDTO, authentication);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(orderResponseDTO, response.getBody());
        verify(orderService, times(1)).create(any(OrderRequestDTO.class), eq(1L));
    }

    @Test
    void testListar() {
        // Arrange
        when(authentication.getName()).thenReturn("1"); // Simula o ID do cliente
        when(orderService.listByClient(1L)).thenReturn(Collections.singletonList(orderResponseDTO));

        // Act
        ResponseEntity<List<OrderResponseDTO>> response = orderController.listOrder(authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(orderResponseDTO, response.getBody().get(0));
        verify(orderService, times(1)).listByClient(1L);
    }

    @Test
    void testBuscarPorId() {
        // Arrange
        when(authentication.getName()).thenReturn("1"); // Simula o ID do cliente
        when(orderService.searchById(1L, 1L)).thenReturn(orderResponseDTO);

        // Act
        ResponseEntity<OrderResponseDTO> response = orderController.findById(1L, authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderResponseDTO, response.getBody());
        verify(orderService, times(1)).searchById(1L, 1L);
    }

    @Test
    void testCancelar() {
        // Arrange
        when(authentication.getName()).thenReturn("1"); // Simula o ID do cliente

        // Act
        ResponseEntity<Void> response = orderController.cancel(1L, authentication);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderService, times(1)).cancel(1L, 1L);
    }

    @Test
    void testHistorico() {
        // Arrange
        when(authentication.getName()).thenReturn("1"); // Simula o ID do cliente
        when(orderService.history(1L)).thenReturn(Collections.singletonList(orderResponseDTO));

        // Act
        ResponseEntity<List<OrderResponseDTO>> response = orderController.history(authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(orderResponseDTO, response.getBody().get(0));
        verify(orderService, times(1)).history(1L);
    }
}
