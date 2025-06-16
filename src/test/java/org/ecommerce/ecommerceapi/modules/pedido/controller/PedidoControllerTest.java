package org.ecommerce.ecommerceapi.modules.pedido.controller;

import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoRequestDTO;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoResponseDTO;
import org.ecommerce.ecommerceapi.modules.pedido.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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

class PedidoControllerTest {

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private Authentication authentication;

    private PedidoRequestDTO pedidoRequestDTO;
    private PedidoResponseDTO pedidoResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pedidoRequestDTO = new PedidoRequestDTO();
        // Configure pedidoRequestDTO conforme necessário

        pedidoResponseDTO = new PedidoResponseDTO();
        pedidoResponseDTO.setId(1L);
        // Configure pedidoResponseDTO conforme necessário
    }

    @Test
    void testCriar() {
        // Arrange
        when(authentication.getName()).thenReturn("1"); // Simula o ID do cliente
        when(pedidoService.criar(any(PedidoRequestDTO.class), eq(1L))).thenReturn(pedidoResponseDTO);

        // Act
        ResponseEntity<PedidoResponseDTO> response = pedidoController.criar(pedidoRequestDTO, authentication);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pedidoResponseDTO, response.getBody());
        verify(pedidoService, times(1)).criar(any(PedidoRequestDTO.class), eq(1L));
    }

    @Test
    void testListar() {
        // Arrange
        when(authentication.getName()).thenReturn("1"); // Simula o ID do cliente
        when(pedidoService.listarPorCliente(1L)).thenReturn(Collections.singletonList(pedidoResponseDTO));

        // Act
        ResponseEntity<List<PedidoResponseDTO>> response = pedidoController.listar(authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(pedidoResponseDTO, response.getBody().get(0));
        verify(pedidoService, times(1)).listarPorCliente(1L);
    }

    @Test
    void testBuscarPorId() {
        // Arrange
        when(authentication.getName()).thenReturn("1"); // Simula o ID do cliente
        when(pedidoService.buscarPorId(1L, 1L)).thenReturn(pedidoResponseDTO);

        // Act
        ResponseEntity<PedidoResponseDTO> response = pedidoController.buscarPorId(1L, authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidoResponseDTO, response.getBody());
        verify(pedidoService, times(1)).buscarPorId(1L, 1L);
    }

    @Test
    void testCancelar() {
        // Arrange
        when(authentication.getName()).thenReturn("1"); // Simula o ID do cliente

        // Act
        ResponseEntity<Void> response = pedidoController.cancelar(1L, authentication);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(pedidoService, times(1)).cancelar(1L, 1L);
    }

    @Test
    void testHistorico() {
        // Arrange
        when(authentication.getName()).thenReturn("1"); // Simula o ID do cliente
        when(pedidoService.historico(1L)).thenReturn(Collections.singletonList(pedidoResponseDTO));

        // Act
        ResponseEntity<List<PedidoResponseDTO>> response = pedidoController.historico(authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(pedidoResponseDTO, response.getBody().get(0));
        verify(pedidoService, times(1)).historico(1L);
    }
}
