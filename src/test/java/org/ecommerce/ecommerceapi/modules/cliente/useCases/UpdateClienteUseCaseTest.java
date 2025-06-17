package org.ecommerce.ecommerceapi.modules.cliente.useCases;

import org.ecommerce.ecommerceapi.modules.cliente.dto.UpdateClienteDTO;
import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateClienteUseCaseTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private UpdateClienteUseCase updateClienteUseCase;

    private ClienteEntity cliente;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        cliente = new ClienteEntity();
        cliente.setId(1L);
        cliente.setNome("Carlos");
        cliente.setUsername("carlos123");
        cliente.setEmail("carlos@email.com");
        cliente.setTelefone("123456789");
        cliente.setEndereco("Endereço Antigo");
        cliente.setCidade("Cidade Antiga");
        cliente.setEstado("Estado Antigo");
        cliente.setCep("00000-000");
    }

    @Test
    void testUpdateClienteFields() {
        // Arrange
        Long clienteId = 1L;
        UpdateClienteDTO updateClienteDTO = new UpdateClienteDTO();
        updateClienteDTO.setTelefone("987654321");
        updateClienteDTO.setEndereco("Novo Endereço");
        updateClienteDTO.setCidade("Nova Cidade");
        updateClienteDTO.setEstado("Novo Estado");
        updateClienteDTO.setCep("11111-111");

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(cliente);

        // Act
        ClienteEntity updatedCliente = updateClienteUseCase.execute(clienteId, updateClienteDTO);

        // Assert
        assertEquals("987654321", updatedCliente.getTelefone());
        assertEquals("Novo Endereço", updatedCliente.getEndereco());
        assertEquals("Nova Cidade", updatedCliente.getCidade());
        assertEquals("Novo Estado", updatedCliente.getEstado());
        assertEquals("11111-111", updatedCliente.getCep());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void testUpdateClienteWithNullFields() {
        // Arrange
        Long clienteId = 1L;
        UpdateClienteDTO updateClienteDTO = new UpdateClienteDTO(); // Não definindo nenhum campo para atualizar

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(cliente);

        // Act
        ClienteEntity updatedCliente = updateClienteUseCase.execute(clienteId, updateClienteDTO);

        // Assert
        assertEquals("123456789", updatedCliente.getTelefone());
        assertEquals("Endereço Antigo", updatedCliente.getEndereco());
        assertEquals("Cidade Antiga", updatedCliente.getCidade());
        assertEquals("Estado Antigo", updatedCliente.getEstado());
        assertEquals("00000-000", updatedCliente.getCep());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void testUpdateClienteWithExistingUsernameOrEmail() {
        // Arrange
        Long clienteId = 1L;
        UpdateClienteDTO updateClienteDTO = new UpdateClienteDTO();
        updateClienteDTO.setUsername("existingUsername");
        updateClienteDTO.setEmail("existing@email.com");

        // Simulando que já existe um cliente com o mesmo username ou email
        ClienteEntity existingCliente = new ClienteEntity();
        existingCliente.setId(2L);
        existingCliente.setUsername("existingUsername");
        existingCliente.setEmail("existing@email.com");

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clienteRepository.findByUsernameOrEmail("existingUsername", "existing@email.com"))
                .thenReturn(Optional.of(existingCliente));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            updateClienteUseCase.execute(clienteId, updateClienteDTO);
        });

        assertEquals("Cliente já existe", exception.getMessage());
    }

    @Test
    void testUpdateClienteWithNonExistingClient() {
        // Arrange
        Long clienteId = 1L;
        UpdateClienteDTO updateClienteDTO = new UpdateClienteDTO();
        updateClienteDTO.setUsername("newUsername");

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            updateClienteUseCase.execute(clienteId, updateClienteDTO);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
    }
}
