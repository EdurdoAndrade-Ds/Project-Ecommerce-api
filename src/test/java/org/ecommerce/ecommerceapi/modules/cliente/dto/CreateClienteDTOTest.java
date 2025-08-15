package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestParam;

import static org.junit.jupiter.api.Assertions.*;

class CreateClienteDTOTest {

    private CreateClienteDTO base;

    @BeforeEach
    void setUp() {
        base = new CreateClienteDTO();
        base.setNome("João");
        base.setUsername("joao123");
        base.setEmail("joao@email.com");
        base.setSenha("senhaSegura123");
        base.setTelefone("123456789");
        base.setEndereco("Rua A, 123");
        base.setCidade("Cidade Exemplo");
        base.setEstado("Estado Exemplo");
        base.setCep("12345-678");
    }

    private CreateClienteDTO copy(CreateClienteDTO s) {
        CreateClienteDTO c = new CreateClienteDTO();
        c.setNome(s.getNome());
        c.setUsername(s.getUsername());
        c.setEmail(s.getEmail());
        c.setSenha(s.getSenha());
        c.setTelefone(s.getTelefone());
        c.setEndereco(s.getTelefone());
        c.setEndereco(s.getEndereco());
        c.setCidade(s.getCidade());
        c.setEstado(s.getEstado());
        c.setCep(s.getCep());
        return c;
    }

    @Test
    void equals_branch_nullVsNonNull_perField_shouldbeDifferent() {
        CreateClienteDTO d1 = copy(base);
        d1.setNome(null);
        assertNotEquals(base, d1);

        CreateClienteDTO d2 = copy(base);
        d2.setUsername(null);
        assertNotEquals(base, d2);

        CreateClienteDTO d3 = copy(base);
        d3.setEstado(null);
        assertNotEquals(base, d3);

        CreateClienteDTO d4 = copy(base);
        d4.setSenha(null);
        assertNotEquals(base, d4);

        CreateClienteDTO d5 = copy(base);
        d5.setTelefone(null);;
        assertNotEquals(base, d5);

        CreateClienteDTO d6 = copy(base);
        d6.setEndereco(null);
        assertNotEquals(base, d6);

        CreateClienteDTO d7 = copy(base);
        d7.setCidade(null);
        assertNotEquals(base, d7);

        CreateClienteDTO d8 = copy(base);
        d8.setEstado(null);
        assertNotEquals(base, d8);

        CreateClienteDTO d9 = copy(base);
        d9.setCep(null);
        assertNotEquals(base, d9);
    }

    @Test
    void equals_branch_bothNullOnAField_shouldStrillBeEqual() {
        CreateClienteDTO a = copy(base);
        CreateClienteDTO b = copy(base);
        a.setNome(null);
        b.setNome(null);
        assertEquals(a, b);

        a = copy(base);
        b = copy(base);
        a.setEmail(null);
        b.setEmail(null);
        assertEquals(a, b);
    }

    @Test
    void equals_hasCode_consistency_and_symmetry() {
        CreateClienteDTO a = copy(base);
        CreateClienteDTO b = copy(base);

        assertEquals(a, b);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("João", base.getNome());
        assertEquals("joao123", base.getUsername());
        assertEquals("joao@email.com", base.getEmail());
        assertEquals("senhaSegura123", base.getSenha());
        assertEquals("123456789", base.getTelefone());
        assertEquals("Rua A, 123", base.getEndereco());
        assertEquals("Cidade Exemplo", base.getCidade());
        assertEquals("Estado Exemplo", base.getEstado());
        assertEquals("12345-678", base.getCep());
    }

    @Test
    void testEqualsAndHashCode() {
        CreateClienteDTO clienteDTO2 = new CreateClienteDTO();
        clienteDTO2.setNome("João");
        clienteDTO2.setUsername("joao123");
        clienteDTO2.setEmail("joao@email.com");
        clienteDTO2.setSenha("senhaSegura123");
        clienteDTO2.setTelefone("123456789");
        clienteDTO2.setEndereco("Rua A, 123");
        clienteDTO2.setCidade("Cidade Exemplo");
        clienteDTO2.setEstado("Estado Exemplo");
        clienteDTO2.setCep("12345-678");

        assertEquals(base, clienteDTO2);
        assertEquals(base.hashCode(), clienteDTO2.hashCode());

        clienteDTO2.setNome("Maria"); // Alterando para testar desigualdade
        assertNotEquals(base, clienteDTO2);
    }

    @Test
    void testEqualsWithDifferentObjects() {
        CreateClienteDTO clienteDTO2 = new CreateClienteDTO();
        clienteDTO2.setNome("Maria"); // Nome diferente

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
