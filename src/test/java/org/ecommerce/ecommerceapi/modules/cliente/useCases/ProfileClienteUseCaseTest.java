package org.ecommerce.ecommerceapi.modules.cliente.useCases;

import org.ecommerce.ecommerceapi.modules.cliente.dto.ProfileClienteResponseDTO;
import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileClienteUseCaseTest {

    private ProfileClienteUseCase profileClienteUseCase;
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setup() {
        clienteRepository = mock(ClienteRepository.class);
        profileClienteUseCase = new ProfileClienteUseCase();
        profileClienteUseCase.clienteRepository = clienteRepository;
    }

    @Test
    void execute_deveRetornarDTO_quandoClienteExistir() {
        Long clienteId = 1L;

        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("João Silva");
        cliente.setUsername("joaosilva");
        cliente.setEmail("joao@email.com");
        cliente.setTelefone("123456789");
        cliente.setEndereco("Rua A, 123");
        cliente.setCidade("São Paulo");
        cliente.setEstado("SP");
        cliente.setCep("12345-678");

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        ProfileClienteResponseDTO dto = profileClienteUseCase.execute(clienteId);

        assertNotNull(dto);
        assertEquals("João Silva", dto.getNome());
        assertEquals("joaosilva", dto.getUsername());
        assertEquals("joao@email.com", dto.getEmail());
        assertEquals("123456789", dto.getTelefone());
        assertEquals("Rua A, 123", dto.getEndereco());
        assertEquals("São Paulo", dto.getCidade());
        assertEquals("SP", dto.getEstado());
        assertEquals("12345-678", dto.getCep());

        verify(clienteRepository, times(1)).findById(clienteId);
    }

    @Test
    void execute_deveLancarExcecao_quandoClienteNaoExistir() {
        Long clienteId = 2L;

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            profileClienteUseCase.execute(clienteId);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());

        verify(clienteRepository, times(1)).findById(clienteId);
    }
}
