package org.ecommerce.ecommerceapi.modules.client.useCases;

import org.ecommerce.ecommerceapi.modules.client.dto.UpdateClientDTO;
import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateClientUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private UpdateClientUseCase updateClientUseCase;

    private ClientEntity cliente;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        cliente = new ClientEntity();
        cliente.setId(1L);
        cliente.setName("Carlos");
        cliente.setUsername("carlos123");
        cliente.setEmail("carlos@email.com");
        cliente.setPhone("123456789");
        cliente.setAddress("Endereço Antigo");
        cliente.setCity("Cidade Antiga");
        cliente.setState("Estado Antigo");
        cliente.setCep("00000-000");
    }

    @Test
    void testUpdateClienteFields() {
        // Arrange
        Long clienteId = 1L;
        UpdateClientDTO updateClientDTO = new UpdateClientDTO();
        updateClientDTO.setPhone("987654321");
        updateClientDTO.setAddress("Novo Endereço");
        updateClientDTO.setCity("Nova Cidade");
        updateClientDTO.setState("Novo Estado");
        updateClientDTO.setCep("11111-111");

        when(clientRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(cliente);

        // Act
        ClientEntity updatedCliente = updateClientUseCase.execute(clienteId, updateClientDTO);

        // Assert
        assertEquals("987654321", updatedCliente.getPhone());
        assertEquals("Novo Endereço", updatedCliente.getAddress());
        assertEquals("Nova Cidade", updatedCliente.getCity());
        assertEquals("Novo Estado", updatedCliente.getState());
        assertEquals("11111-111", updatedCliente.getCep());
        verify(clientRepository).save(cliente);
    }

    @Test
    void testUpdateClienteWithNullFields() {
        // Arrange
        Long clienteId = 1L;
        UpdateClientDTO updateClientDTO = new UpdateClientDTO(); // Não definindo nenhum campo para atualizar

        when(clientRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(cliente);

        // Act
        ClientEntity updatedCliente = updateClientUseCase.execute(clienteId, updateClientDTO);

        // Assert
        assertEquals("123456789", updatedCliente.getPhone());
        assertEquals("Endereço Antigo", updatedCliente.getAddress());
        assertEquals("Cidade Antiga", updatedCliente.getCity());
        assertEquals("Estado Antigo", updatedCliente.getState());
        assertEquals("00000-000", updatedCliente.getCep());
        verify(clientRepository).save(cliente);
    }

    @Test
    void testUpdateClienteWithExistingUsernameOrEmail() {
        // Arrange
        Long clienteId = 1L;
        UpdateClientDTO updateClientDTO = new UpdateClientDTO();
        updateClientDTO.setUsername("existingUsername");
        updateClientDTO.setEmail("existing@email.com");

        // Simulando que já existe um cliente com o mesmo username ou email
        ClientEntity existingCliente = new ClientEntity();
        existingCliente.setId(2L);
        existingCliente.setUsername("existingUsername");
        existingCliente.setEmail("existing@email.com");

        when(clientRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clientRepository.findByUsernameOrEmail("existingUsername", "existing@email.com"))
                .thenReturn(Optional.of(existingCliente));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            updateClientUseCase.execute(clienteId, updateClientDTO);
        });

        assertEquals("Cliente já existe", exception.getMessage());
    }

    @Test
    void testUpdateClienteWithNonExistingClient() {
        // Arrange
        Long clienteId = 1L;
        UpdateClientDTO updateClientDTO = new UpdateClientDTO();
        updateClientDTO.setUsername("newUsername");

        when(clientRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            updateClientUseCase.execute(clienteId, updateClientDTO);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
    }
    
    @Test
    void testUpdateClienteCep() {
        UpdateClientDTO updateClientDTO = new UpdateClientDTO();
        updateClientDTO.setCep("98765-432");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(cliente);
        ClientEntity updatedCliente = updateClientUseCase.execute(1L, updateClientDTO);
        assertEquals("98765-432", updatedCliente.getCep());
        verify(clientRepository).save(cliente);
    }
}