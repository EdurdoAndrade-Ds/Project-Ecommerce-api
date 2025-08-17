package org.ecommerce.ecommerceapi.modules.client.useCases;

import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteClientUseCaseTest {

    @InjectMocks
    private DeleteClientUseCase deleteClientUseCase;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_deveDesativarClienteComSucesso() {
        Long clienteId = 1L;
        String senhaCorreta = "senha123";

        ClientEntity clienteExistente = new ClientEntity();
        clienteExistente.setId(clienteId);
        clienteExistente.setPassword(passwordEncoder.encode(senhaCorreta)); // Senha codificada
        clienteExistente.setActive(true);

        when(clientRepository.findById(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(passwordEncoder.matches(senhaCorreta, clienteExistente.getPassword())).thenReturn(true);
        when(clientRepository.save(clienteExistente)).thenReturn(clienteExistente);

        deleteClientUseCase.execute(clienteId, senhaCorreta);

        assertFalse(clienteExistente.isActive());
        verify(clientRepository, times(1)).findById(clienteId);
        verify(passwordEncoder, times(1)).matches(senhaCorreta, clienteExistente.getPassword());
        verify(clientRepository, times(1)).save(clienteExistente);
    }

    @Test
    void execute_deveLancarRuntimeExceptionQuandoClienteNaoEncontrado() {
        Long clienteId = 2L;
        String senha = "senhaQualquer";

        when(clientRepository.findById(clienteId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            deleteClientUseCase.execute(clienteId, senha);
        });

        assertEquals("Cliente não encontrado", ex.getMessage());
        verify(clientRepository, times(1)).findById(clienteId);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(clientRepository, never()).save(any());
    }

    @Test
    void execute_deveLancarRuntimeExceptionQuandoSenhaIncorreta() {
        Long clienteId = 3L;
        String senhaIncorreta = "senhaErrada";
        String senhaCorreta = "senha123";

        ClientEntity clienteExistente = new ClientEntity();
        clienteExistente.setId(clienteId);
        clienteExistente.setPassword(passwordEncoder.encode(senhaCorreta)); // Senha codificada
        clienteExistente.setActive(true);

        when(clientRepository.findById(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(passwordEncoder.matches(senhaIncorreta, clienteExistente.getPassword())).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            deleteClientUseCase.execute(clienteId, senhaIncorreta);
        });

        assertEquals("Senha incorreta", ex.getMessage());
        verify(clientRepository, times(1)).findById(clienteId);
        verify(passwordEncoder, times(1)).matches(senhaIncorreta, clienteExistente.getPassword());
        verify(clientRepository, never()).save(any());
    }
}
