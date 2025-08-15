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

    private UpdateClienteDTO copy(UpdateClienteDTO s) {
        UpdateClienteDTO c = new UpdateClienteDTO();
        c.setNome(s.getNome());
        c.setUsername(s.getUsername());
        c.setEmail(s.getEmail());
        c.setTelefone(s.getTelefone());
        c.setEndereco(s.getEndereco());
        c.setCidade(s.getCidade());
        c.setEstado(s.getEstado());
        c.setCep(s.getCep());
        return c;
    }

    @Test
    void equals_branch_nullVsNonNull_perField_souldBeDifferent() {
        UpdateClienteDTO d1 = copy(clienteDTO)
                ;
        d1.setNome(null);
        assertNotEquals(clienteDTO, d1);

        UpdateClienteDTO d2 = copy(clienteDTO);
        d2.setUsername(null);
        assertNotEquals(clienteDTO, d2);


        UpdateClienteDTO d3 = copy(clienteDTO);
        d3.setEmail(null);
        assertNotEquals(clienteDTO, d3);


        UpdateClienteDTO d4 = copy(clienteDTO);
        d4.setTelefone(null);
        assertNotEquals(clienteDTO, d4);


        UpdateClienteDTO d5 = copy(clienteDTO);
        d5.setEndereco(null);
        assertNotEquals(clienteDTO, d5);


        UpdateClienteDTO d6 = copy(clienteDTO);
        d6.setCidade(null);
        assertNotEquals(clienteDTO, d6);


        UpdateClienteDTO d7 = copy(clienteDTO);
        d7.setEstado(null);
        assertNotEquals(clienteDTO, d7);

        UpdateClienteDTO d8 = copy(clienteDTO);
        d8.setCep(null);
        assertNotEquals(clienteDTO, d8);
    }

    @Test
    void equals_branch_bothNullOnAField_shouldStillBeEqual() {
        UpdateClienteDTO a = copy(clienteDTO);
        UpdateClienteDTO b = copy(clienteDTO);
        a.setNome(null);
        b.setNome(null);
        assertEquals(a, b);

        a = copy(clienteDTO);
        b = copy(clienteDTO);
        a.setEmail(null);
        b.setEmail(null);
        assertEquals(a, b);
    }

    @Test
    void equals_hashCode_consistency_and_symmetry() {
        UpdateClienteDTO a = copy(clienteDTO);
        UpdateClienteDTO b = copy(clienteDTO);

        assertEquals(a, b);
        assertEquals(b, a);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
