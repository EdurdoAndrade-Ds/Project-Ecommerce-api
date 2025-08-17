package org.ecommerce.ecommerceapi.modules.client.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateClientDTOTest {

    private UpdateClientDTO clienteDTO;

    @BeforeEach
    void setUp() {
        clienteDTO = new UpdateClientDTO();
        clienteDTO.setName("João");
        clienteDTO.setUsername("joao123");
        clienteDTO.setEmail("joao@email.com");
        clienteDTO.setPhone("123456789");
        clienteDTO.setAddress("Rua A, 123");
        clienteDTO.setCity("Cidade Exemplo");
        clienteDTO.setState("Estado Exemplo");
        clienteDTO.setCep("12345-678");
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("João", clienteDTO.getName());
        assertEquals("joao123", clienteDTO.getUsername());
        assertEquals("joao@email.com", clienteDTO.getEmail());
        assertEquals("123456789", clienteDTO.getPhone());
        assertEquals("Rua A, 123", clienteDTO.getAddress());
        assertEquals("Cidade Exemplo", clienteDTO.getCity());
        assertEquals("Estado Exemplo", clienteDTO.getState());
        assertEquals("12345-678", clienteDTO.getCep());
    }

    @Test
    void testEqualsAndHashCode() {
        UpdateClientDTO clienteDTO2 = new UpdateClientDTO();
        clienteDTO2.setName("João");
        clienteDTO2.setUsername("joao123");
        clienteDTO2.setEmail("joao@email.com");
        clienteDTO2.setPhone("123456789");
        clienteDTO2.setAddress("Rua A, 123");
        clienteDTO2.setCity("Cidade Exemplo");
        clienteDTO2.setState("Estado Exemplo");
        clienteDTO2.setCep("12345-678");

        assertEquals(clienteDTO, clienteDTO2);
        assertEquals(clienteDTO.hashCode(), clienteDTO2.hashCode());

        clienteDTO2.setName("Maria"); // Alterando para testar desigualdade
        assertNotEquals(clienteDTO, clienteDTO2);
    }

    @Test
    void testEqualsWithDifferentObjects() {
        UpdateClientDTO clienteDTO2 = new UpdateClientDTO();
        clienteDTO2.setName("Maria"); // Nome diferente

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

    private UpdateClientDTO copy(UpdateClientDTO s) {
        UpdateClientDTO c = new UpdateClientDTO();
        c.setName(s.getName());
        c.setUsername(s.getUsername());
        c.setEmail(s.getEmail());
        c.setPhone(s.getPhone());
        c.setAddress(s.getAddress());
        c.setCity(s.getCity());
        c.setState(s.getState());
        c.setCep(s.getCep());
        return c;
    }

    @Test
    void equals_branch_nullVsNonNull_perField_souldBeDifferent() {
        UpdateClientDTO d1 = copy(clienteDTO);
        d1.setName(null);
        assertNotEquals(clienteDTO, d1);

        UpdateClientDTO d2 = copy(clienteDTO);
        d2.setUsername(null);
        assertNotEquals(clienteDTO, d2);


        UpdateClientDTO d3 = copy(clienteDTO);
        d3.setEmail(null);
        assertNotEquals(clienteDTO, d3);


        UpdateClientDTO d4 = copy(clienteDTO);
        d4.setPhone(null);
        assertNotEquals(clienteDTO, d4);


        UpdateClientDTO d5 = copy(clienteDTO);
        d5.setAddress(null);
        assertNotEquals(clienteDTO, d5);


        UpdateClientDTO d6 = copy(clienteDTO);
        d6.setCity(null);
        assertNotEquals(clienteDTO, d6);


        UpdateClientDTO d7 = copy(clienteDTO);
        d7.setState(null);
        assertNotEquals(clienteDTO, d7);

        UpdateClientDTO d8 = copy(clienteDTO);
        d8.setCep(null);
        assertNotEquals(clienteDTO, d8);
    }

    @Test
    void equals_branch_bothNullOnAField_shouldStillBeEqual() {
        UpdateClientDTO a = copy(clienteDTO);
        UpdateClientDTO b = copy(clienteDTO);
        a.setName(null);
        b.setName(null);
        assertEquals(a, b);

        a = copy(clienteDTO);
        b = copy(clienteDTO);
        a.setEmail(null);
        b.setEmail(null);
        assertEquals(a, b);
    }

    @Test
    void equals_hashCode_consistency_and_symmetry() {
        UpdateClientDTO a = copy(clienteDTO);
        UpdateClientDTO b = copy(clienteDTO);

        assertEquals(a, b);
        assertEquals(b, a);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
