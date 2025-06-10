package org.ecommerce.ecommerceapi.modules.pedido.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoRequestDTO;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoResponseDTO;
import org.ecommerce.ecommerceapi.modules.pedido.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TestPedidoController {

    private MockMvc mockMvc;

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private Authentication authentication;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final Long clienteId = 1L;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();

        // Mocka o clienteId na autenticação
        when(authentication.getName()).thenReturn(clienteId.toString());
    }

    @Test
    void criar() throws Exception {
        PedidoRequestDTO requestDTO = new PedidoRequestDTO();
        // preencha os campos necessários do DTO

        PedidoResponseDTO responseDTO = new PedidoResponseDTO();
        responseDTO.setId(1L);
        // preencha os campos necessários do DTO

        when(pedidoService.criar(any(PedidoRequestDTO.class), eq(clienteId))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO))
                .principal(authentication))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));

        verify(pedidoService, times(1)).criar(any(PedidoRequestDTO.class), eq(clienteId));
    }

    @Test
    void listar() throws Exception {
        PedidoResponseDTO p1 = new PedidoResponseDTO();
        p1.setId(1L);
        PedidoResponseDTO p2 = new PedidoResponseDTO();
        p2.setId(2L);

        when(pedidoService.listarPorCliente(clienteId)).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/api/pedidos")
                .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));

        verify(pedidoService, times(1)).listarPorCliente(clienteId);
    }

    @Test
    void buscarPorId() throws Exception {
        PedidoResponseDTO responseDTO = new PedidoResponseDTO();
        responseDTO.setId(1L);

        when(pedidoService.buscarPorId(1L, clienteId)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/pedidos/{id}", 1L)
                .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(pedidoService, times(1)).buscarPorId(1L, clienteId);
    }

    @Test
    void cancelar() throws Exception {
        doNothing().when(pedidoService).cancelar(1L, clienteId);

        mockMvc.perform(delete("/api/pedidos/{id}", 1L)
                .principal(authentication))
                .andExpect(status().isNoContent());

        verify(pedidoService, times(1)).cancelar(1L, clienteId);
    }

    @Test
    void historico() throws Exception {
        PedidoResponseDTO p1 = new PedidoResponseDTO();
        p1.setId(1L);
        PedidoResponseDTO p2 = new PedidoResponseDTO();
        p2.setId(2L);

        when(pedidoService.historico(clienteId)).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/api/pedidos/historico")
                .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));

        verify(pedidoService, times(1)).historico(clienteId);
    }
}
