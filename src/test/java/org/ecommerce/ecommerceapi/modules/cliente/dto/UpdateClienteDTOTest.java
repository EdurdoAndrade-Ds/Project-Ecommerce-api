package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateClienteDTOTest {

    private UpdateClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        clienteDTO = new UpdateClienteDTO();
        clienteDTO.setNome("João");
        clienteDTO.setUsername("joao123");
        clienteDTO.setEmail("joao@email.com");
        clienteDTO.setTelefone("123456789");
        clienteDTO.setEndereco("Rua A, 123");
        clienteDTO.setCidade("Cidade Exemplo");
        clienteDTO.setEstado("Estado Exemplo");
        clienteDTO.setCep("12345-678");
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("João", clienteDTO.getNome());
        assertEquals("joao123", clienteDTO.getUsername());
        assertEquals("joao@email.com", clienteDTO.getEmail());
        assertEquals("123456789", clienteDTO.getTelefone());
        assertEquals("Rua A, 123", clienteDTO.getEndereco());
        assertEquals("Cidade Exemplo", clienteDTO.getCidade());
        assertEquals("Estado Exemplo", clienteDTO.getEstado());
        assertEquals("12345-678", clienteDTO.getCep());
    }

    @Test
    void testEqualsAndHashCode() {
        UpdateClienteDTO clienteDTO2 = new UpdateClienteDTO();
        clienteDTO2.setNome("João");
        clienteDTO2.setUsername("joao123");
        clienteDTO2.setEmail("joao@email.com");
        clienteDTO2.setTelefone("123456789");
        clienteDTO2.setEndereco("Rua A, 123");
        clienteDTO2.setCidade("Cidade Exemplo");
        clienteDTO2.setEstado("Estado Exemplo");
        clienteDTO2.setCep("12345-678");

        assertEquals(clienteDTO, clienteDTO2);
        assertEquals(clienteDTO.hashCode(), clienteDTO2.hashCode());

        clienteDTO2.setNome("Maria"); // Alterando para testar desigualdade
        assertNotEquals(clienteDTO, clienteDTO2);
    }

    @Test
    void testEqualsWithDifferentObjects() {
        UpdateClienteDTO clienteDTO2 = new UpdateClienteDTO();
        clienteDTO2.setNome("Maria"); // Nome diferente

        assertNotEquals(clienteDTO, clienteDTO2);
    }

    @Test
    void testEqualsWithNull() {
        assertNotEquals(clienteDTO, null);
    }

    @Test
    void testEqualsWithDifferentClass() {
        String differentClassObject = "This is a string";
        assertNotEquals(clienteDTO, differentClassObject);
    }
}
