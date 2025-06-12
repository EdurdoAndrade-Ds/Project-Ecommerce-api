package org.ecommerce.ecommerceapi.modules.pedido.controller;

import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoRequestDTO;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoResponseDTO;
import org.ecommerce.ecommerceapi.modules.pedido.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PedidoControllerTest {

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriar() {
        PedidoRequestDTO requestDTO = new PedidoRequestDTO();
        requestDTO.setItens(Arrays.asList(new PedidoRequestDTO.ItemDTO()));

        PedidoResponseDTO responseDTO = new PedidoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTotal(BigDecimal.valueOf(100.00));

        when(authentication.getName()).thenReturn("1"); // Simulando o ID do cliente
        when(pedidoService.criar(any(PedidoRequestDTO.class), anyLong())).thenReturn(responseDTO);

        ResponseEntity<PedidoResponseDTO> response = pedidoController.criar(requestDTO, authentication);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void testListar() {
        PedidoResponseDTO responseDTO = new PedidoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTotal(BigDecimal.valueOf(100.00));

        when(authentication.getName()).thenReturn("1"); // Simulando o ID do cliente
        when(pedidoService.listarPorCliente(anyLong())).thenReturn(Arrays.asList(responseDTO));

        ResponseEntity<List<PedidoResponseDTO>> response = pedidoController.listar(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(responseDTO, response.getBody().get(0));
    }

    @Test
    void testBuscarPorId() {
        PedidoResponseDTO responseDTO = new PedidoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTotal(BigDecimal.valueOf(100.00));

        when(authentication.getName()).thenReturn("1"); // Simulando o ID do cliente
        when(pedidoService.buscarPorId(anyLong(), anyLong())).thenReturn(responseDTO);

        ResponseEntity<PedidoResponseDTO> response = pedidoController.buscarPorId(1L, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void testCancelar() {
        when(authentication.getName()).thenReturn("1"); // Simulando o ID do cliente

        ResponseEntity<Void> response = pedidoController.cancelar(1L, authentication);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(pedidoService, times(1)).cancelar(1L, 1L);
    }

    @Test
    void testHistorico() {
        PedidoResponseDTO responseDTO = new PedidoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTotal(BigDecimal.valueOf(100.00));

        when(authentication.getName()).thenReturn("1"); // Simulando o ID do cliente
        when(pedidoService.historico(anyLong())).thenReturn(Arrays.asList(responseDTO));

        ResponseEntity<List<PedidoResponseDTO>> response = pedidoController.historico(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(responseDTO, response.getBody().get(0));
    }
}
