package org.ecommerce.ecommerceapi.modules.cliente.useCases;

import org.ecommerce.ecommerceapi.modules.cliente.dto.UpdateClienteDTO;
import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateClienteUseCaseTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private UpdateClienteUseCase updateClienteUseCase;

    public UpdateClienteUseCaseTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateClienteFields() {
        // Arrange
        Long clienteId = 1L;
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(clienteId);
        cliente.setNome("Nome Antigo");
        cliente.setTelefone("123456789");
        cliente.setEndereco("Endereço Antigo");
        cliente.setCidade("Cidade Antiga");
        cliente.setEstado("Estado Antigo");
        cliente.setCep("00000-000");

        UpdateClienteDTO updateClienteDTO = new UpdateClienteDTO();
        updateClienteDTO.setTelefone("987654321");
        updateClienteDTO.setEndereco("Novo Endereço");
        updateClienteDTO.setCidade("Nova Cidade");
        updateClienteDTO.setEstado("Novo Estado");
        updateClienteDTO.setCep("11111-111");

        when(clienteRepository.findById(clienteId)).thenReturn(java.util.Optional.of(cliente));
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
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(clienteId);
        cliente.setNome("Nome Antigo");
        cliente.setTelefone("123456789");
        cliente.setEndereco("Endereço Antigo");
        cliente.setCidade("Cidade Antiga");
        cliente.setEstado("Estado Antigo");
        cliente.setCep("00000-000");

        UpdateClienteDTO updateClienteDTO = new UpdateClienteDTO();
        // Não definindo nenhum campo para atualizar

        when(clienteRepository.findById(clienteId)).thenReturn(java.util.Optional.of(cliente));
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
}
