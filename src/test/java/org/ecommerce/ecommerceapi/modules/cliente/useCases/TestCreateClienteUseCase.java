package org.ecommerce.ecommerceapi.modules.cliente.useCases;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestCreateClienteUseCase {

    private CreateClienteUseCase createClienteUseCase;
    private ClienteRepository clienteRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        clienteRepository = mock(ClienteRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);

        createClienteUseCase = new CreateClienteUseCase();
        createClienteUseCase.clienteRepository = clienteRepository;
        createClienteUseCase.passwordEncoder = passwordEncoder;
    }

    @Test
    void execute_deveCriarClienteComSucesso() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setUsername("usuario1");
        clienteEntity.setEmail("usuario1@email.com");
        clienteEntity.setSenha("senha123");

        when(clienteRepository.findByUsernameOrEmail("usuario1", "usuario1@email.com"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("senha123")).thenReturn("senhaCriptografada");

        ClienteEntity clienteSalvo = new ClienteEntity();
        clienteSalvo.setId(1L);
        clienteSalvo.setUsername("usuario1");
        clienteSalvo.setEmail("usuario1@email.com");
        clienteSalvo.setSenha("senhaCriptografada");
        clienteSalvo.setAtivo(true);

        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteSalvo);

        ClienteEntity resultado = createClienteUseCase.execute(clienteEntity);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("usuario1", resultado.getUsername());
        assertEquals("senhaCriptografada", resultado.getSenha());
        assertTrue(resultado.isAtivo());

        verify(clienteRepository, times(1)).findByUsernameOrEmail("usuario1", "usuario1@email.com");
        verify(passwordEncoder, times(1)).encode("senha123");
        verify(clienteRepository, times(1)).save(any(ClienteEntity.class));
    }

    @Test
    void execute_deveLancarExcecaoQuandoClienteExiste() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setUsername("usuarioExistente");
        clienteEntity.setEmail("existente@email.com");
        clienteEntity.setSenha("senha123");

        when(clienteRepository.findByUsernameOrEmail("usuarioExistente", "existente@email.com"))
                .thenReturn(Optional.of(new ClienteEntity()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            createClienteUseCase.execute(clienteEntity);
        });

        assertEquals("Cliente já existe", exception.getMessage());

        verify(clienteRepository, times(1)).findByUsernameOrEmail("usuarioExistente", "existente@email.com");
        verify(passwordEncoder, never()).encode(anyString());
        verify(clienteRepository, never()).save(any(ClienteEntity.class));
    }
}
