package org.ecommerce.ecommerceapi.modules.client.controllers;

import org.ecommerce.ecommerceapi.modules.client.dto.CreateClientDTO;
import org.ecommerce.ecommerceapi.modules.client.dto.DeleteClientDTO;
import org.ecommerce.ecommerceapi.modules.client.dto.UpdateClientDTO;
import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.useCases.CreateClientUseCase;
import org.ecommerce.ecommerceapi.modules.client.useCases.DeleteClientUseCase;
import org.ecommerce.ecommerceapi.modules.client.useCases.UpdateClientUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import jakarta.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ClientControllerTest {

    private ClientController clientController;
    private CreateClientUseCase createUseCase;
    private DeleteClientUseCase deleteUseCase;
    private UpdateClientUseCase updateUseCase;

    private Authentication authentication;

    @BeforeEach
    void setUp() throws Exception {
        createUseCase = mock(CreateClientUseCase.class);
        deleteUseCase = mock(DeleteClientUseCase.class);
        updateUseCase = mock(UpdateClientUseCase.class);
        authentication = mock(Authentication.class);

        clientController = new ClientController();

        var f1 = ClientController.class.getDeclaredField("createClienteUseCase");
        f1.setAccessible(true);
        f1.set(clientController, createUseCase);

        var f2 = ClientController.class.getDeclaredField("deleteClienteUseCase");
        f2.setAccessible(true);
        f2.set(clientController, deleteUseCase);

        var f3 = ClientController.class.getDeclaredField("updateClienteUseCase");
        f3.setAccessible(true);
        f3.set(clientController, updateUseCase);
    }

    @Test
    void deveCriarClienteComSucesso() {
        CreateClientDTO dto = new CreateClientDTO();
        dto.setName("João Silva");
        dto.setUsername("joaosilva");
        dto.setEmail("joao@email.com");
        dto.setPassword("senha123");
        dto.setPhone("(11) 99999-9999");
        dto.setAddress("Rua das Flores, 123");
        dto.setCity("São Paulo");
        dto.setState("SP");
        dto.setCep("01234-567");

        ClientEntity entityMock = ClientEntity.builder()
                .id(1L)
                .name(dto.getName())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build();

        when(createUseCase.execute(any(ClientEntity.class))).thenReturn(entityMock);

        ResponseEntity<Object> response = clientController.create(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entityMock, response.getBody());
    }

    @Test
    void deveDeletarClienteComSucesso() {
        DeleteClientDTO dto = new DeleteClientDTO();
        dto.setPassword("senha123");

        when(authentication.getName()).thenReturn("1");

        ResponseEntity<Object> response = clientController.delete(dto, authentication);

        assertEquals(204, response.getStatusCodeValue());
        verify(deleteUseCase, times(1)).execute(1L, "senha123");
    }

    @Test
    void deveAtualizarClienteComSucesso() {
        UpdateClientDTO dto = new UpdateClientDTO();
        dto.setName("João Atualizado");

        ClientEntity atualizado = ClientEntity.builder()
                .id(1L)
                .name("João Atualizado")
                .build();

        when(authentication.getName()).thenReturn("1");
        when(updateUseCase.execute(1L, dto)).thenReturn(atualizado);

        ResponseEntity<Object> response = clientController.update(dto, authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(atualizado, response.getBody());
    }

    @Test
    void testCreateCliente_comErro() {
        CreateClientDTO dto = new CreateClientDTO();
        dto.setName("Erro");

        when(createUseCase.execute(any())).thenThrow(new RuntimeException("Cliente já existe"));

        ResponseEntity<Object> response = clientController.create(dto);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Cliente já existe", response.getBody());
    }

    @Test
    void testDeleteCliente_comErro() {
        DeleteClientDTO dto = new DeleteClientDTO();
        dto.setPassword("senhaErrada");

        when(authentication.getName()).thenReturn("1");
        doThrow(new RuntimeException("Senha incorreta")).when(deleteUseCase).execute(anyLong(), any());

        ResponseEntity<Object> response = clientController.delete(dto, authentication);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Senha incorreta", response.getBody());
    }

    @Test
    void testUpdateCliente_notFound() {
        UpdateClientDTO dto = new UpdateClientDTO();
        dto.setEmail("novo@email.com");

        when(authentication.getName()).thenReturn("1");
        when(updateUseCase.execute(eq(1L), eq(dto)))
            .thenThrow(new EntityNotFoundException("Cliente não encontrado"));

        ResponseEntity<Object> response = clientController.update(dto, authentication);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Cliente não encontrado", response.getBody());
    }
}   