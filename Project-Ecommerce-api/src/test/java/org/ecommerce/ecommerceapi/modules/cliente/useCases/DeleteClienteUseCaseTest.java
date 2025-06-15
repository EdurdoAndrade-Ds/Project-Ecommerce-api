package org.ecommerce.ecommerceapi.modules.cliente.useCases;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.ecommerce.ecommerceapi.modules.cliente.useCases.DeleteClienteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteClienteUseCaseTest {

    @InjectMocks
    private DeleteClienteUseCase deleteClienteUseCase;

    @Mock
    private ClienteRepository clienteRepository;

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

        ClienteEntity clienteExistente = new ClienteEntity();
        clienteExistente.setId(clienteId);
        clienteExistente.setSenha(passwordEncoder.encode(senhaCorreta)); // Senha codificada
        clienteExistente.setAtivo(true);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(passwordEncoder.matches(senhaCorreta, clienteExistente.getSenha())).thenReturn(true);
        when(clienteRepository.save(clienteExistente)).thenReturn(clienteExistente);

        deleteClienteUseCase.execute(clienteId, senhaCorreta);

        assertFalse(clienteExistente.isAtivo());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(passwordEncoder, times(1)).matches(senhaCorreta, clienteExistente.getSenha());
        verify(clienteRepository, times(1)).save(clienteExistente);
    }

    @Test
    void execute_deveLancarRuntimeExceptionQuandoClienteNaoEncontrado() {
        Long clienteId = 2L;
        String senha = "senhaQualquer";

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            deleteClienteUseCase.execute(clienteId, senha);
        });

        assertEquals("Cliente não encontrado", ex.getMessage());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void execute_deveLancarRuntimeExceptionQuandoSenhaIncorreta() {
        Long clienteId = 3L;
        String senhaIncorreta = "senhaErrada";
        String senhaCorreta = "senha123";

        ClienteEntity clienteExistente = new ClienteEntity();
        clienteExistente.setId(clienteId);
        clienteExistente.setSenha(passwordEncoder.encode(senhaCorreta)); // Senha codificada
        clienteExistente.setAtivo(true);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(passwordEncoder.matches(senhaIncorreta, clienteExistente.getSenha())).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            deleteClienteUseCase.execute(clienteId, senhaIncorreta);
        });

        assertEquals("Senha incorreta", ex.getMessage());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(passwordEncoder, times(1)).matches(senhaIncorreta, clienteExistente.getSenha());
        verify(clienteRepository, never()).save(any());
    }
}
