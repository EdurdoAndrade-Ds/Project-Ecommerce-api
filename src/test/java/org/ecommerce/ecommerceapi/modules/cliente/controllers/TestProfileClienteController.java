package org.ecommerce.ecommerceapi.modules.cliente.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.ecommerce.ecommerceapi.modules.cliente.dto.ProfileClienteResponseDTO;
import org.ecommerce.ecommerceapi.modules.cliente.useCases.ProfileClienteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestProfileClienteController {

    private ProfileClienteController controller;
    private ProfileClienteUseCase profileUseCase;

    @BeforeEach
    void setUp() {
        profileUseCase = mock(ProfileClienteUseCase.class);
        controller = new ProfileClienteController(profileUseCase);
    }

    @Test
    void deveRetornarPerfilDoClienteComSucesso() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("cliente_id")).thenReturn(1L); // tipo Long, não String

        ProfileClienteResponseDTO dto = new ProfileClienteResponseDTO();
        dto.setNome("João Silva");
        dto.setUsername("joaosilva");
        dto.setEmail("joao@email.com");

        when(profileUseCase.execute(1L)).thenReturn(dto);

        // Act
        ResponseEntity<Object> response = controller.profile(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
        verify(profileUseCase, times(1)).execute(1L);
    }

    @Test
    void deveRetornar401QuandoAtributoNaoExistirOuForInvalido() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("cliente_id")).thenReturn(null);

        // Act
        ResponseEntity<Object> response = controller.profile(request);

        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof String);
    }
}
