package org.ecommerce.ecommerceapi.modules.client.useCases;

import org.ecommerce.ecommerceapi.exceptions.AuthenticationException;
import org.ecommerce.ecommerceapi.modules.client.dto.AuthClientDTO;
import org.ecommerce.ecommerceapi.modules.client.dto.AuthClientResponseDTO;
import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.ecommerce.ecommerceapi.providers.JWTProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthClientUseCaseTest {

    private AuthClientUseCase authClientUseCase;
    private ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;
    private JWTProvider jwtProvider;

    @BeforeEach
    void setup() {
        clientRepository = mock(ClientRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtProvider = mock(JWTProvider.class);

        authClientUseCase = new AuthClientUseCase();
        authClientUseCase.clientRepository = clientRepository;
        authClientUseCase.passwordEncoder = passwordEncoder;
        authClientUseCase.jwtProvider = jwtProvider;
    }

    @Test
    void execute_sucesso() {
        AuthClientDTO dto = new AuthClientDTO();
        dto.setUsername("user1");
        dto.setPassword("senha123");

        ClientEntity cliente = new ClientEntity();
        cliente.setId(1L);
        cliente.setUsername("user1");
        cliente.setPassword("hash_da_senha");
        cliente.setActive(true);

        when(clientRepository.findByUsernameOrEmail("user1", "user1")).thenReturn(Optional.of(cliente));
        when(passwordEncoder.matches("senha123", "hash_da_senha")).thenReturn(true);
        when(jwtProvider.generateToken("1", java.util.Arrays.asList("CLIENTE"))).thenReturn("token123");

        AuthClientResponseDTO response = authClientUseCase.execute(dto);

        assertNotNull(response);
        assertEquals("token123", response.getToken());
        assertEquals(1L, response.getId());
        assertEquals("user1", response.getUsername());

        verify(clientRepository, times(1)).findByUsernameOrEmail("user1", "user1");
        verify(passwordEncoder, times(1)).matches("senha123", "hash_da_senha");
        verify(jwtProvider, times(1)).generateToken("1", java.util.Arrays.asList("CLIENTE"));
    }

    @Test
    void execute_falhaUsuarioNaoEncontrado() {
        AuthClientDTO dto = new AuthClientDTO();
        dto.setUsername("userInvalido");
        dto.setPassword("senha123");

        when(clientRepository.findByUsernameOrEmail("userInvalido", "userInvalido")).thenReturn(Optional.empty());

        assertThrows(AuthenticationException.class, () -> authClientUseCase.execute(dto));

        verify(clientRepository, times(1)).findByUsernameOrEmail("userInvalido", "userInvalido");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtProvider, never()).generateToken(anyString(), anyList());
    }

    @Test
    void execute_falhaSenhaIncorreta() {
        AuthClientDTO dto = new AuthClientDTO();
        dto.setUsername("user1");
        dto.setPassword("senhaErrada");

        ClientEntity cliente = new ClientEntity();
        cliente.setId(1L);
        cliente.setUsername("user1");
        cliente.setPassword("hash_da_senha");
        cliente.setActive(true);

        when(clientRepository.findByUsernameOrEmail("user1", "user1")).thenReturn(Optional.of(cliente));
        when(passwordEncoder.matches("senhaErrada", "hash_da_senha")).thenReturn(false);

        assertThrows(AuthenticationException.class, () -> authClientUseCase.execute(dto));

        verify(clientRepository, times(1)).findByUsernameOrEmail("user1", "user1");
        verify(passwordEncoder, times(1)).matches("senhaErrada", "hash_da_senha");
        verify(jwtProvider, never()).generateToken(anyString(), anyList());
    }

    @Test
    void execute_falhaClienteDesativado() {
        AuthClientDTO dto = new AuthClientDTO();
        dto.setUsername("user1");
        dto.setPassword("senha123");

        ClientEntity cliente = new ClientEntity();
        cliente.setId(1L);
        cliente.setUsername("user1");
        cliente.setPassword("hash_da_senha");
        cliente.setActive(false); // cliente desativado

        when(clientRepository.findByUsernameOrEmail("user1", "user1")).thenReturn(Optional.of(cliente));
        when(passwordEncoder.matches("senha123", "hash_da_senha")).thenReturn(true);

        AuthenticationException ex = assertThrows(AuthenticationException.class, () -> authClientUseCase.execute(dto));
        assertEquals("Cliente desativado", ex.getMessage());

        verify(clientRepository, times(1)).findByUsernameOrEmail("user1", "user1");
        verify(passwordEncoder, times(1)).matches("senha123", "hash_da_senha");
        verify(jwtProvider, never()).generateToken(anyString(), anyList());
    }
}
