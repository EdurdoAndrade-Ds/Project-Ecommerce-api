package org.ecommerce.ecommerceapi.modules.cliente.useCases;

import org.ecommerce.ecommerceapi.exceptions.AuthenticationException;
import org.ecommerce.ecommerceapi.modules.cliente.dto.AuthClienteDTO;
import org.ecommerce.ecommerceapi.modules.cliente.dto.AuthClienteResponseDTO;
import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.ecommerce.ecommerceapi.providers.JWTProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestAuthClienteUseCase {

    private AuthClienteUseCase authClienteUseCase;
    private ClienteRepository clienteRepository;
    private PasswordEncoder passwordEncoder;
    private JWTProvider jwtProvider;

    @BeforeEach
    void setup() {
        clienteRepository = mock(ClienteRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtProvider = mock(JWTProvider.class);

        authClienteUseCase = new AuthClienteUseCase();
        authClienteUseCase.clienteRepository = clienteRepository;
        authClienteUseCase.passwordEncoder = passwordEncoder;
        authClienteUseCase.jwtProvider = jwtProvider;
    }

    @Test
    void execute_sucesso() {
        AuthClienteDTO dto = new AuthClienteDTO();
        dto.setUsername("user1");
        dto.setSenha("senha123");

        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(1L);
        cliente.setUsername("user1");
        cliente.setSenha("hash_da_senha");
        cliente.setAtivo(true);

        when(clienteRepository.findByUsernameOrEmail("user1", "user1")).thenReturn(Optional.of(cliente));
        when(passwordEncoder.matches("senha123", "hash_da_senha")).thenReturn(true);
        when(jwtProvider.generateToken("1", java.util.Arrays.asList("CLIENTE"))).thenReturn("token123");

        AuthClienteResponseDTO response = authClienteUseCase.execute(dto);

        assertNotNull(response);
        assertEquals("token123", response.getToken());
        assertEquals(1L, response.getId());
        assertEquals("user1", response.getUsername());

        verify(clienteRepository, times(1)).findByUsernameOrEmail("user1", "user1");
        verify(passwordEncoder, times(1)).matches("senha123", "hash_da_senha");
        verify(jwtProvider, times(1)).generateToken("1", java.util.Arrays.asList("CLIENTE"));
    }

    @Test
    void execute_falhaUsuarioNaoEncontrado() {
        AuthClienteDTO dto = new AuthClienteDTO();
        dto.setUsername("userInvalido");
        dto.setSenha("senha123");

        when(clienteRepository.findByUsernameOrEmail("userInvalido", "userInvalido")).thenReturn(Optional.empty());

        assertThrows(AuthenticationException.class, () -> authClienteUseCase.execute(dto));

        verify(clienteRepository, times(1)).findByUsernameOrEmail("userInvalido", "userInvalido");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtProvider, never()).generateToken(anyString(), anyList());
    }

    @Test
    void execute_falhaSenhaIncorreta() {
        AuthClienteDTO dto = new AuthClienteDTO();
        dto.setUsername("user1");
        dto.setSenha("senhaErrada");

        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(1L);
        cliente.setUsername("user1");
        cliente.setSenha("hash_da_senha");
        cliente.setAtivo(true);

        when(clienteRepository.findByUsernameOrEmail("user1", "user1")).thenReturn(Optional.of(cliente));
        when(passwordEncoder.matches("senhaErrada", "hash_da_senha")).thenReturn(false);

        assertThrows(AuthenticationException.class, () -> authClienteUseCase.execute(dto));

        verify(clienteRepository, times(1)).findByUsernameOrEmail("user1", "user1");
        verify(passwordEncoder, times(1)).matches("senhaErrada", "hash_da_senha");
        verify(jwtProvider, never()).generateToken(anyString(), anyList());
    }

    @Test
    void execute_falhaClienteDesativado() {
        AuthClienteDTO dto = new AuthClienteDTO();
        dto.setUsername("user1");
        dto.setSenha("senha123");

        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(1L);
        cliente.setUsername("user1");
        cliente.setSenha("hash_da_senha");
        cliente.setAtivo(false); // cliente desativado

        when(clienteRepository.findByUsernameOrEmail("user1", "user1")).thenReturn(Optional.of(cliente));
        when(passwordEncoder.matches("senha123", "hash_da_senha")).thenReturn(true);

        AuthenticationException ex = assertThrows(AuthenticationException.class, () -> authClienteUseCase.execute(dto));
        assertEquals("Cliente desativado", ex.getMessage());

        verify(clienteRepository, times(1)).findByUsernameOrEmail("user1", "user1");
        verify(passwordEncoder, times(1)).matches("senha123", "hash_da_senha");
        verify(jwtProvider, never()).generateToken(anyString(), anyList());
    }
}
