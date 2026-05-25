package org.ecommerce.ecommerceapi.modules.client.useCases;

import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Optional;

class CreateClientUseCaseTest {

    private CreateClientUseCase createClientUseCase;
    private ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        clientRepository = mock(ClientRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);

        createClientUseCase = new CreateClientUseCase();
        createClientUseCase.clientRepository = clientRepository;
        createClientUseCase.passwordEncoder = passwordEncoder;
    }

    @Test
    void execute_deveCriarClienteComSucesso() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setUsername("usuario1");
        clientEntity.setEmail("usuario1@email.com");
        clientEntity.setPassword("senha123");

        when(clientRepository.findByUsernameOrEmail("usuario1", "usuario1@email.com"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("senha123")).thenReturn("senhaCriptografada");

        ClientEntity clienteSalvo = new ClientEntity();
        clienteSalvo.setId(1L);
        clienteSalvo.setUsername("usuario1");
        clienteSalvo.setEmail("usuario1@email.com");
        clienteSalvo.setPassword("senhaCriptografada");
        clienteSalvo.setActive(true);

        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clienteSalvo);

        ClientEntity resultado = createClientUseCase.execute(clientEntity);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("usuario1", resultado.getUsername());
        assertEquals("senhaCriptografada", resultado.getPassword());
        assertTrue(resultado.isActive());

        verify(clientRepository, times(1)).findByUsernameOrEmail("usuario1", "usuario1@email.com");
        verify(passwordEncoder, times(1)).encode("senha123");
        verify(clientRepository, times(1)).save(any(ClientEntity.class));
    }

    @Test
    void execute_deveLancarExcecaoQuandoClienteExiste() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setUsername("usuarioExistente");
        clientEntity.setEmail("existente@email.com");
        clientEntity.setPassword("senha123");

        when(clientRepository.findByUsernameOrEmail("usuarioExistente", "existente@email.com"))
                .thenReturn(Optional.of(new ClientEntity()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            createClientUseCase.execute(clientEntity);
        });

        assertEquals("Cliente já existe", exception.getMessage());

        verify(clientRepository, times(1)).findByUsernameOrEmail("usuarioExistente", "existente@email.com");
        verify(passwordEncoder, never()).encode(anyString());
        verify(clientRepository, never()).save(any(ClientEntity.class));
    }
    @Test
    void testEqualsAndHashCode() {
        ClientEntity a = new ClientEntity();
        a.setId(1L);

        ClientEntity b = new ClientEntity();
        b.setId(1L);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        ClientEntity t1 = new ClientEntity();
        ClientEntity t2 = new ClientEntity();
        assertNotEquals(t1, t2);

        b.setId(2L);
        assertNotEquals(a, b);

        ClientEntity c = new ClientEntity();
        c.setId(null);
        assertNotEquals(a, c);

    }
}