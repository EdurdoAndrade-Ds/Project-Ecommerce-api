package org.ecommerce.ecommerceapi.modules.cliente.controllers;

import org.ecommerce.ecommerceapi.modules.cliente.dto.AuthClienteDTO;
import org.ecommerce.ecommerceapi.modules.cliente.dto.AuthClienteResponseDTO;
import org.ecommerce.ecommerceapi.modules.cliente.useCases.AuthClienteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthClienteControllerTest {

    private AuthClienteUseCase authClienteUseCase;
    private AuthClienteController authClienteController;

    @BeforeEach
    void setUp() {
        authClienteUseCase = mock(AuthClienteUseCase.class);
        authClienteController = new AuthClienteController(authClienteUseCase);
    }

    @Test
    void deveRetornar200QuandoAutenticacaoForBemSucedida() {
        // Arrange
        AuthClienteDTO dto = new AuthClienteDTO();
        dto.setUsername("joaosilva");
        dto.setSenha("senha123456");

        AuthClienteResponseDTO responseMock = new AuthClienteResponseDTO() {
            public String token = "fake-jwt-token";
            public Long id = 1L;
            public String username = "joaosilva";
        };

        when(authClienteUseCase.execute(dto)).thenReturn(responseMock);

        // Act
        ResponseEntity<Object> response = authClienteController.auth(dto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseMock, response.getBody());
        verify(authClienteUseCase, times(1)).execute(dto);
    }

    @Test
    void deveRetornar401QuandoCredenciaisForemInvalidas() {
        // Arrange
        AuthClienteDTO dto = new AuthClienteDTO();
        dto.setUsername("joaosilva");
        dto.setSenha("senha_errada");

        when(authClienteUseCase.execute(dto))
                .thenThrow(new RuntimeException("Usuário ou senha incorretos"));

        // Act
        ResponseEntity<Object> response = authClienteController.auth(dto);

        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Usuário ou senha incorretos", response.getBody());
        verify(authClienteUseCase, times(1)).execute(dto);
    }
}
