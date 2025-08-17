package org.ecommerce.ecommerceapi.modules.client.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateClientDTOTest {

    private CreateClientDTO base;

    @BeforeEach
    void setUp() {
        base = new CreateClientDTO();
        base.setName("João");
        base.setUsername("joao123");
        base.setEmail("joao@email.com");
        base.setPassword("senhaSegura123");
        base.setPhone("123456789");
        base.setAddress("Rua A, 123");
        base.setCity("Cidade Exemplo");
        base.setState("Estado Exemplo");
        base.setCep("12345-678");
    }

    private CreateClientDTO copy(CreateClientDTO s) {
        CreateClientDTO c = new CreateClientDTO();
        c.setName(s.getName());
        c.setUsername(s.getUsername());
        c.setEmail(s.getEmail());
        c.setPassword(s.getPassword());
        c.setPhone(s.getPhone());
        c.setAddress(s.getPhone());
        c.setAddress(s.getAddress());
        c.setCity(s.getCity());
        c.setState(s.getState());
        c.setCep(s.getCep());
        return c;
    }

    @Test
    void equals_branch_nullVsNonNull_perField_shouldbeDifferent() {
        CreateClientDTO d1 = copy(base);
        d1.setName(null);
        assertNotEquals(base, d1);

        CreateClientDTO d2 = copy(base);
        d2.setUsername(null);
        assertNotEquals(base, d2);

        CreateClientDTO d3 = copy(base);
        d3.setState(null);
        assertNotEquals(base, d3);

        CreateClientDTO d4 = copy(base);
        d4.setPassword(null);
        assertNotEquals(base, d4);

        CreateClientDTO d5 = copy(base);
        d5.setPhone(null);;
        assertNotEquals(base, d5);

        CreateClientDTO d6 = copy(base);
        d6.setAddress(null);
        assertNotEquals(base, d6);

        CreateClientDTO d7 = copy(base);
        d7.setCity(null);
        assertNotEquals(base, d7);

        CreateClientDTO d8 = copy(base);
        d8.setState(null);
        assertNotEquals(base, d8);

        CreateClientDTO d9 = copy(base);
        d9.setCep(null);
        assertNotEquals(base, d9);
    }

    @Test
    void equals_branch_bothNullOnAField_shouldStrillBeEqual() {
        CreateClientDTO a = copy(base);
        CreateClientDTO b = copy(base);
        a.setName(null);
        b.setName(null);
        assertEquals(a, b);

        a = copy(base);
        b = copy(base);
        a.setEmail(null);
        b.setEmail(null);
        assertEquals(a, b);
    }

    @Test
    void equals_hasCode_consistency_and_symmetry() {
        CreateClientDTO a = copy(base);
        CreateClientDTO b = copy(base);

        assertEquals(a, b);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("João", base.getName());
        assertEquals("joao123", base.getUsername());
        assertEquals("joao@email.com", base.getEmail());
        assertEquals("senhaSegura123", base.getPassword());
        assertEquals("123456789", base.getPhone());
        assertEquals("Rua A, 123", base.getAddress());
        assertEquals("Cidade Exemplo", base.getCity());
        assertEquals("Estado Exemplo", base.getState());
        assertEquals("12345-678", base.getCep());
    }

    @Test
    void testEqualsAndHashCode() {
        CreateClientDTO clienteDTO2 = new CreateClientDTO();
        clienteDTO2.setName("João");
        clienteDTO2.setUsername("joao123");
        clienteDTO2.setEmail("joao@email.com");
        clienteDTO2.setPassword("senhaSegura123");
        clienteDTO2.setPhone("123456789");
        clienteDTO2.setAddress("Rua A, 123");
        clienteDTO2.setCity("Cidade Exemplo");
        clienteDTO2.setState("Estado Exemplo");
        clienteDTO2.setCep("12345-678");

        assertEquals(base, clienteDTO2);
        assertEquals(base.hashCode(), clienteDTO2.hashCode());

        clienteDTO2.setName("Maria"); // Alterando para testar desigualdade
        assertNotEquals(base, clienteDTO2);
    }

    @Test
    void testEqualsWithDifferentObjects() {
        CreateClientDTO clienteDTO2 = new CreateClientDTO();
        clienteDTO2.setName("Maria"); // Nome diferente

        assertNotEquals(base, clienteDTO2);
    }

    @Test
    void testEqualsWithNull() {
        assertNotEquals(base, null);
    }

    @Test
    void testEqualsWithDifferentClass() {
        String differentClassObject = "This is a string";
        assertNotEquals(base, differentClassObject);
    }
}
