package org.ecommerce.ecommerceapi.modules.client.controllers;

import org.ecommerce.ecommerceapi.modules.client.dto.AuthClientDTO;
import org.ecommerce.ecommerceapi.modules.client.dto.AuthClientResponseDTO;
import org.ecommerce.ecommerceapi.modules.client.useCases.AuthClientUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthClientControllerTest {

    private AuthClientUseCase authClientUseCase;
    private AuthClientController authClientController;

    @BeforeEach
    void setUp() {
        authClientUseCase = mock(AuthClientUseCase.class);
        authClientController = new AuthClientController(authClientUseCase);
    }

    @Test
    void deveRetornar200QuandoAutenticacaoForBemSucedida() {
        // Arrange
        AuthClientDTO dto = new AuthClientDTO();
        dto.setUsername("joaosilva");
        dto.setPassword("senha123456");

        AuthClientResponseDTO responseMock = new AuthClientResponseDTO() {
            public String token = "fake-jwt-token";
            public Long id = 1L;
            public String username = "joaosilva";
        };

        when(authClientUseCase.execute(dto)).thenReturn(responseMock);

        // Act
        ResponseEntity<Object> response = authClientController.auth(dto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseMock, response.getBody());
        verify(authClientUseCase, times(1)).execute(dto);
    }

    @Test
    void deveRetornar401QuandoCredenciaisForemInvalidas() {
        // Arrange
        AuthClientDTO dto = new AuthClientDTO();
        dto.setUsername("joaosilva");
        dto.setPassword("senha_errada");

        when(authClientUseCase.execute(dto))
                .thenThrow(new RuntimeException("Usuário ou senha incorretos"));

        // Act
        ResponseEntity<Object> response = authClientController.auth(dto);

        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Usuário ou senha incorretos", response.getBody());
        verify(authClientUseCase, times(1)).execute(dto);
    }
}
