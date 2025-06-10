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

class TestDeleteClienteUseCase {

    private DeleteClienteUseCase deleteClienteUseCase;
    private ClienteRepository clienteRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        clienteRepository = mock(ClienteRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);

        deleteClienteUseCase = new DeleteClienteUseCase();
        deleteClienteUseCase.clienteRepository = clienteRepository;
        deleteClienteUseCase.passwordEncoder = passwordEncoder;
    }

    @Test
    void execute_deveDesativarClienteComSenhaCorreta() {
        Long clienteId = 1L;
        String senhaCorreta = "senha123";

        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(clienteId);
        cliente.setSenha("senhaCriptografada");
        cliente.setAtivo(true);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(passwordEncoder.matches(senhaCorreta, "senhaCriptografada")).thenReturn(true);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        deleteClienteUseCase.execute(clienteId, senhaCorreta);

        assertFalse(cliente.isAtivo());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void execute_deveLancarExcecaoQuandoClienteNaoExistir() {
        Long clienteId = 2L;
        String senha = "qualquer";

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            deleteClienteUseCase.execute(clienteId, senha);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void execute_deveLancarExcecaoQuandoSenhaIncorreta() {
        Long clienteId = 3L;
        String senhaIncorreta = "senhaErrada";

        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(clienteId);
        cliente.setSenha("senhaCriptografada");
        cliente.setAtivo(true);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(passwordEncoder.matches(senhaIncorreta, "senhaCriptografada")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            deleteClienteUseCase.execute(clienteId, senhaIncorreta);
        });

        assertEquals("Senha incorreta", exception.getMessage());
        verify(clienteRepository, never()).save(any());
    }
}
