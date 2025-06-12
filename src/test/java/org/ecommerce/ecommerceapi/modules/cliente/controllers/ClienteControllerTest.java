package org.ecommerce.ecommerceapi.modules.cliente.controllers;

import org.ecommerce.ecommerceapi.modules.cliente.dto.CreateClienteDTO;
import org.ecommerce.ecommerceapi.modules.cliente.dto.DeleteClienteDTO;
import org.ecommerce.ecommerceapi.modules.cliente.dto.UpdateClienteDTO;
import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.useCases.CreateClienteUseCase;
import org.ecommerce.ecommerceapi.modules.cliente.useCases.DeleteClienteUseCase;
import org.ecommerce.ecommerceapi.modules.cliente.useCases.UpdateClienteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    private ClienteController clienteController;
    private CreateClienteUseCase createUseCase;
    private DeleteClienteUseCase deleteUseCase;
    private UpdateClienteUseCase updateUseCase;

    @BeforeEach
    void setUp() throws Exception {
        createUseCase = mock(CreateClienteUseCase.class);
        deleteUseCase = mock(DeleteClienteUseCase.class);
        updateUseCase = mock(UpdateClienteUseCase.class);

        clienteController = new ClienteController();

        var f1 = ClienteController.class.getDeclaredField("createClienteUseCase");
        f1.setAccessible(true);
        f1.set(clienteController, createUseCase);

        var f2 = ClienteController.class.getDeclaredField("deleteClienteUseCase");
        f2.setAccessible(true);
        f2.set(clienteController, deleteUseCase);

        var f3 = ClienteController.class.getDeclaredField("updateClienteUseCase");
        f3.setAccessible(true);
        f3.set(clienteController, updateUseCase);
    }

    @Test
    void deveCriarClienteComSucesso() {
        CreateClienteDTO dto = new CreateClienteDTO();
        dto.setNome("João Silva");
        dto.setUsername("joaosilva");
        dto.setEmail("joao@email.com");
        dto.setSenha("senha123");
        dto.setTelefone("(11) 99999-9999");
        dto.setEndereco("Rua das Flores, 123");
        dto.setCidade("São Paulo");
        dto.setEstado("SP");
        dto.setCep("01234-567");

        ClienteEntity entityMock = ClienteEntity.builder()
                .id(1L)
                .nome(dto.getNome())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build();

        when(createUseCase.execute(any(ClienteEntity.class))).thenReturn(entityMock);

        ResponseEntity<Object> response = clienteController.create(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entityMock, response.getBody());
    }

    @Test
    void deveDeletarClienteComSucesso() {
        DeleteClienteDTO dto = new DeleteClienteDTO();
        dto.setSenha("senha123");

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("1");

        ResponseEntity<Object> response = clienteController.delete(dto, auth);

        assertEquals(204, response.getStatusCodeValue());
        verify(deleteUseCase, times(1)).execute(1L, "senha123");
    }

    @Test
    void deveAtualizarClienteComSucesso() {
        UpdateClienteDTO dto = new UpdateClienteDTO();
        dto.setNome("João Atualizado");

        ClienteEntity atualizado = ClienteEntity.builder()
                .id(1L)
                .nome("João Atualizado")
                .build();

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("1");

        when(updateUseCase.execute(1L, dto)).thenReturn(atualizado);

        ResponseEntity<Object> response = clienteController.update(dto, auth);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(atualizado, response.getBody());
    }
    @Test
    void create_deveCadastrarClienteComSucesso() {
        CreateClienteDTO dto = new CreateClienteDTO();
        dto.setNome("João Silva");
        dto.setUsername("joaosilva");
        dto.setEmail("joao@email.com");
        dto.setSenha("senha123");
        dto.setTelefone("(11) 99999-9999");
        dto.setEndereco("Rua das Flores, 123");
        dto.setCidade("São Paulo");
        dto.setEstado("SP");
        dto.setCep("01234-567");

        ClienteEntity entityMock = ClienteEntity.builder()
                .id(1L)
                .nome(dto.getNome())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build();

        when(createUseCase.execute(any(ClienteEntity.class))).thenReturn(entityMock);

        ResponseEntity<Object> response = clienteController.create(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(entityMock, response.getBody());
    }

    @Test
    void create_deveRetornarBadRequestQuandoExcecao() {
        CreateClienteDTO dto = new CreateClienteDTO();
        dto.setNome("João Silva");

        when(createUseCase.execute(any(ClienteEntity.class))).thenThrow(new RuntimeException("Cliente já existe"));

        ResponseEntity<Object> response = clienteController.create(dto);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Cliente já existe", response.getBody());
    }

    @Test
    void delete_deveDeletarClienteComSucesso() {
        DeleteClienteDTO dto = new DeleteClienteDTO();
        dto.setSenha("senha123");

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("1");

        doNothing().when(deleteUseCase).execute(1L, "senha123");

        ResponseEntity<Object> response = clienteController.delete(dto, auth);

        assertEquals(204, response.getStatusCodeValue());
        verify(deleteUseCase, times(1)).execute(1L, "senha123");
    }

    @Test
    void delete_deveRetornarBadRequestQuandoSenhaIncorreta() {
        DeleteClienteDTO dto = new DeleteClienteDTO();
        dto.setSenha("senhaErrada");

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("1");

        doThrow(new RuntimeException("Senha incorreta")).when(deleteUseCase).execute(1L, "senhaErrada");

        ResponseEntity<Object> response = clienteController.delete(dto, auth);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Senha incorreta", response.getBody());
    }

    @Test
    void delete_deveRetornarBadRequestQuandoExcecaoGenerica() {
        DeleteClienteDTO dto = new DeleteClienteDTO();
        dto.setSenha("senha123");

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("1");

        doThrow(new RuntimeException("Erro inesperado")).when(deleteUseCase).execute(1L, "senha123");

        ResponseEntity<Object> response = clienteController.delete(dto, auth);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Erro inesperado", response.getBody());
    }

    @Test
    void update_deveAtualizarClienteComSucesso() {
        UpdateClienteDTO dto = new UpdateClienteDTO();
        dto.setNome("João Atualizado");

        ClienteEntity atualizado = ClienteEntity.builder()
                .id(1L)
                .nome("João Atualizado")
                .build();

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("1");

        when(updateUseCase.execute(1L, dto)).thenReturn(atualizado);

        ResponseEntity<Object> response = clienteController.update(dto, auth);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(atualizado, response.getBody());
    }

    @Test
    void update_deveRetornarNotFoundQuandoEntityNotFoundException() {
        UpdateClienteDTO dto = new UpdateClienteDTO();

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("1");

        when(updateUseCase.execute(1L, dto)).thenThrow(new jakarta.persistence.EntityNotFoundException("Cliente não encontrado"));

        ResponseEntity<Object> response = clienteController.update(dto, auth);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Cliente não encontrado", response.getBody());
    }

    @Test
    void update_deveRetornarBadRequestQuandoExcecaoGenerica() {
        UpdateClienteDTO dto = new UpdateClienteDTO();

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("1");

        when(updateUseCase.execute(1L, dto)).thenThrow(new RuntimeException("Erro genérico"));

        ResponseEntity<Object> response = clienteController.update(dto, auth);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Erro genérico", response.getBody());
    }
}
