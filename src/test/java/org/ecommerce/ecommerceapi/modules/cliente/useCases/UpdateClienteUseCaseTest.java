package org.ecommerce.ecommerceapi.modules.cliente.useCases;

import jakarta.persistence.EntityNotFoundException;
import org.ecommerce.ecommerceapi.modules.cliente.dto.UpdateClienteDTO;
import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateClienteUseCaseTest {

    private UpdateClienteUseCase updateClienteUseCase;
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setup() {
        clienteRepository = mock(ClienteRepository.class);
        updateClienteUseCase = new UpdateClienteUseCase();
        updateClienteUseCase.clienteRepository = clienteRepository;
    }

    @Test
    void execute_deveAtualizarClienteComSucesso() {
        Long clienteId = 1L;
        UpdateClienteDTO dto = new UpdateClienteDTO();
        dto.setNome("Novo Nome");
        dto.setUsername("novoUsername");
        dto.setEmail("novo@email.com");

        ClienteEntity clienteExistente = new ClienteEntity();
        clienteExistente.setId(clienteId);
        clienteExistente.setNome("Antigo Nome");
        clienteExistente.setUsername("velhoUsername");
        clienteExistente.setEmail("velho@email.com");

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.findByUsernameOrEmail("novoUsername", "novo@email.com")).thenReturn(Optional.empty());
        when(clienteRepository.save(any(ClienteEntity.class))).thenAnswer(i -> i.getArgument(0));

        ClienteEntity atualizado = updateClienteUseCase.execute(clienteId, dto);

        assertEquals("Novo Nome", atualizado.getNome());
        assertEquals("novoUsername", atualizado.getUsername());
        assertEquals("novo@email.com", atualizado.getEmail());

        verify(clienteRepository, times(1)).findById(clienteId);
        verify(clienteRepository, times(1)).findByUsernameOrEmail("novoUsername", "novo@email.com");
        verify(clienteRepository, times(1)).save(any(ClienteEntity.class));
    }

    @Test
    void execute_deveLancarEntityNotFoundExceptionQuandoClienteNaoEncontrado() {
        Long clienteId = 2L;
        UpdateClienteDTO dto = new UpdateClienteDTO();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            updateClienteUseCase.execute(clienteId, dto);
        });

        assertEquals("Cliente não encontrado", ex.getMessage());

        verify(clienteRepository, times(1)).findById(clienteId);
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void execute_deveLancarRuntimeExceptionQuandoUsernameOuEmailJaExistir() {
        Long clienteId = 3L;
        UpdateClienteDTO dto = new UpdateClienteDTO();
        dto.setUsername("usernameExistente");
        dto.setEmail("email@existente.com");

        ClienteEntity clienteExistente = new ClienteEntity();
        clienteExistente.setId(clienteId);
        clienteExistente.setUsername("usuarioAtual");
        clienteExistente.setEmail("email@atual.com");

        ClienteEntity outroCliente = new ClienteEntity();
        outroCliente.setId(999L); // ID diferente para simular conflito

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.findByUsernameOrEmail("usernameExistente", "email@existente.com")).thenReturn(Optional.of(outroCliente));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            updateClienteUseCase.execute(clienteId, dto);
        });

        assertEquals("Cliente já existe", ex.getMessage());

        verify(clienteRepository, times(1)).findById(clienteId);
        verify(clienteRepository, times(1)).findByUsernameOrEmail("usernameExistente", "email@existente.com");
        verify(clienteRepository, never()).save(any());
    }
}
