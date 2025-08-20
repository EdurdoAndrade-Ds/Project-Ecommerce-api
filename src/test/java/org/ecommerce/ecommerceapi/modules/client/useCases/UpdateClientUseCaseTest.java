package org.ecommerce.ecommerceapi.modules.client.useCases;

import org.ecommerce.ecommerceapi.exceptions.ClientConflictException;
import org.ecommerce.ecommerceapi.exceptions.ClientNotFoundException;
import org.ecommerce.ecommerceapi.modules.client.dto.UpdateClientDTO;
import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.mapper.ClientMapper;
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

    @Mock
    private ClientMapper clientMapper;

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

        // simular o comportamento do mapper
        doAnswer(invocation -> {
            UpdateClientDTO dto = invocation.getArgument(0);
            ClientEntity entity = invocation.getArgument(1);

            if (dto.getPhone() != null) entity.setPhone(dto.getPhone());
            if (dto.getAddress() != null) entity.setAddress(dto.getAddress());
            if (dto.getCity() != null) entity.setCity(dto.getCity());
            if (dto.getState() != null) entity.setState(dto.getState());
            if (dto.getCep() != null) entity.setCep(dto.getCep());
            if (dto.getUsername() != null) entity.setUsername(dto.getUsername());
            if (dto.getEmail() != null) entity.setEmail(dto.getEmail());

            return null;
        }).when(clientMapper).updateFromDto(any(UpdateClientDTO.class), any(ClientEntity.class));
    }

    @Test
    void testUpdateClienteFields() {
        Long clienteId = 1L;
        UpdateClientDTO dto = new UpdateClientDTO();
        dto.setPhone("987654321");
        dto.setAddress("Novo Endereço");
        dto.setCity("Nova Cidade");
        dto.setState("Novo Estado");
        dto.setCep("11111-111");

        when(clientRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(cliente);

        ClientEntity updated = updateClientUseCase.execute(clienteId, dto);

        assertEquals("987654321", updated.getPhone());
        assertEquals("Novo Endereço", updated.getAddress());
        assertEquals("Nova Cidade", updated.getCity());
        assertEquals("Novo Estado", updated.getState());
        assertEquals("11111-111", updated.getCep());
        verify(clientRepository).save(cliente);
    }

    @Test
    void testUpdateClienteWithNullFields() {
        Long clienteId = 1L;
        UpdateClientDTO dto = new UpdateClientDTO(); // sem alterações

        when(clientRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(cliente);

        ClientEntity updated = updateClientUseCase.execute(clienteId, dto);

        assertEquals("123456789", updated.getPhone());
        assertEquals("Endereço Antigo", updated.getAddress());
        assertEquals("Cidade Antiga", updated.getCity());
        assertEquals("Estado Antigo", updated.getState());
        assertEquals("00000-000", updated.getCep());
        verify(clientRepository).save(cliente);
    }

    @Test
    void testUpdateClienteWithExistingUsernameOrEmail() {
        Long clienteId = 1L;
        UpdateClientDTO dto = new UpdateClientDTO();
        dto.setUsername("existingUsername");
        dto.setEmail("existing@email.com");

        when(clientRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clientRepository.existsByUsernameOrEmailAndIdNot("existingUsername", "existing@email.com", clienteId))
                .thenReturn(true);

        assertThrows(ClientConflictException.class, () -> {
            updateClientUseCase.execute(clienteId, dto);
        });
    }

    @Test
    void testUpdateClienteWithNonExistingClient() {
        Long clienteId = 1L;
        UpdateClientDTO dto = new UpdateClientDTO();
        dto.setUsername("newUsername");

        when(clientRepository.findById(clienteId)).thenReturn(Optional.empty());

        ClientNotFoundException ex = assertThrows(ClientNotFoundException.class, () -> {
            updateClientUseCase.execute(clienteId, dto);
        });

        // a mensagem é exatamente como está hardcoded na classe (com typo)
        assertEquals("Cliente nao encontrado", ex.getMessage());
    }

    @Test
    void testUpdateClienteCep() {
        UpdateClientDTO dto = new UpdateClientDTO();
        dto.setCep("98765-432");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(cliente);

        ClientEntity updated = updateClientUseCase.execute(1L, dto);

        assertEquals("98765-432", updated.getCep());
        verify(clientRepository).save(cliente);
    }

    @Test
    void testMapperCalled() {
        Long clienteId = 1L;
        UpdateClientDTO dto = new UpdateClientDTO();
        dto.setCity("Nova Cidade");

        when(clientRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(cliente);

        updateClientUseCase.execute(clienteId, dto);

        // garante que o mapper foi usado
        verify(clientMapper, times(1)).updateFromDto(dto, cliente);
    }


}