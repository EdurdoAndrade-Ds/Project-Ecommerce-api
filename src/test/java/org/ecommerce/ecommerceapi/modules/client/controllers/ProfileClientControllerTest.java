package org.ecommerce.ecommerceapi.modules.client.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.ecommerce.ecommerceapi.exceptions.ClientUnauthorizedException;
import org.ecommerce.ecommerceapi.modules.client.dto.ProfileClientResponseDTO;
import org.ecommerce.ecommerceapi.modules.client.useCases.ProfileClientUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileClientControllerTest {

    private ProfileClientController controller;
    private ProfileClientUseCase profileUseCase;

    @BeforeEach
    void setUp() {
        profileUseCase = mock(ProfileClientUseCase.class);
        controller = new ProfileClientController(profileUseCase);
    }

    @Test
    void deveRetornarPerfilDoClienteComSucesso() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("cliente_id")).thenReturn(1L); // tipo Long, não String

        ProfileClientResponseDTO dto = new ProfileClientResponseDTO();
        dto.setName("João Silva");
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

        // Act & Assert
        try {
            controller.profile(request);
            fail("Deveria lançar ClientUnauthorizedException");
        } catch (ClientUnauthorizedException ex) {
            ResponseEntity<Object> response = controller.handleClientUnauthorized(ex);
            assertEquals(401, response.getStatusCodeValue());
            assertTrue(response.getBody() instanceof String);
        }
    }
}
