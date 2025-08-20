package org.ecommerce.ecommerceapi.modules.client.useCases;

import org.ecommerce.ecommerceapi.exceptions.ClientUnauthorizedException;
import org.ecommerce.ecommerceapi.modules.client.dto.ProfileClientResponseDTO;
import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileClientUseCaseTest {

    private ProfileClientUseCase profileClientUseCase;
    private ClientRepository clientRepository;

    @BeforeEach
    void setup() {
        clientRepository = mock(ClientRepository.class);
        profileClientUseCase = new ProfileClientUseCase();
        profileClientUseCase.clientRepository = clientRepository;
    }

    @Test
    void execute_deveRetornarDTO_quandoClienteExistir() {
        Long clienteId = 1L;

        ClientEntity cliente = new ClientEntity();
        cliente.setName("João Silva");
        cliente.setUsername("joaosilva");
        cliente.setEmail("joao@email.com");
        cliente.setPhone("123456789");
        cliente.setAddress("Rua A, 123");
        cliente.setCity("São Paulo");
        cliente.setState("SP");
        cliente.setCep("12345-678");

        when(clientRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        ProfileClientResponseDTO dto = profileClientUseCase.execute(clienteId);

        assertNotNull(dto);
        assertEquals("João Silva", dto.getName());
        assertEquals("joaosilva", dto.getUsername());
        assertEquals("joao@email.com", dto.getEmail());
        assertEquals("123456789", dto.getPhone());
        assertEquals("Rua A, 123", dto.getAddress());
        assertEquals("São Paulo", dto.getCity());
        assertEquals("SP", dto.getState());
        assertEquals("12345-678", dto.getCep());

        verify(clientRepository, times(1)).findById(clienteId);
    }

    @Test
    void execute_deveLancarExcecao_quandoClienteNaoExistir() {
        Long clienteId = 2L;
        when(clientRepository.findById(clienteId)).thenReturn(Optional.empty());

        ClientUnauthorizedException exception = assertThrows(ClientUnauthorizedException.class, () -> {
            profileClientUseCase.execute(clienteId);
        });

        assertEquals("Cliente nao encontrado", exception.getMessage());

        verify(clientRepository, times(1)).findById(clienteId);
    }
}
