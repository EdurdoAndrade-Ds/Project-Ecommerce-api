package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthClienteDTOTest {

    private AuthClienteDTO make(String username, String senha) {
        AuthClienteDTO dto = new AuthClienteDTO();
        dto.setUsername(username);
        dto.setSenha(senha);
        return dto;
    }

    @Test
    void equals_hashCode_fullBranches() {
        AuthClienteDTO base = make("user", "123");

        assertEquals(base, base);

        AuthClienteDTO igual = make(new String("user"), new String("123"));
        assertEquals(base, igual);
        assertEquals(base.hashCode(), igual.hashCode());

        assertNotEquals(base, null);
        assertNotEquals(base, "string");

        AuthClienteDTO diffUsername = make("user2", "123");
        assertNotEquals(base, diffUsername);

        AuthClienteDTO diffSenha = make("user", "999");
        assertNotEquals(base, diffSenha);

        assertNotEquals(make(null, "123"), make("user", "123"));
        assertNotEquals(make("user", null), make("user", "123"));

        assertEquals(make(null, "123"), make(null, "123"));
        assertEquals(make("user", null), make("user", null));

        assertTrue(base.canEqual(igual));
        assertFalse(base.canEqual("string"));
        assertTrue(base.toString().contains("AuthClienteDTO"));

        AuthClienteDTO viaBuilder = AuthClienteDTO.builder()
                .username("user")
                .senha("123")
                .build();
        assertEquals(base, viaBuilder);
    }

    @Test
    void testHashCodeWithNulls() {

        AuthClienteDTO ambosNull = make(null, null);
        ambosNull.hashCode();

        AuthClienteDTO usernameNull = make(null, "123");
        usernameNull.hashCode();

        AuthClienteDTO senhaNull = make("user", null);
        senhaNull.hashCode();
    }

    @Test
    void testGettersAndSetters() {
        AuthClienteDTO dto = new AuthClienteDTO();
        dto.setUsername("usuario");
        dto.setSenha("senha123");

        assertEquals("usuario", dto.getUsername());
        assertEquals("senha123", dto.getSenha());

        // Testando setters com null
        dto.setUsername(null);
        dto.setSenha(null);
        assertNull(dto.getUsername());
        assertNull(dto.getSenha());
    }

    @Test
    void testEqualsAndHashCode() {
        AuthClienteDTO dto1 = AuthClienteDTO.builder()
                .username("user")
                .senha("pass")
                .build();

        AuthClienteDTO dto2 = AuthClienteDTO.builder()
                .username("user")
                .senha("pass")
                .build();

        AuthClienteDTO dto3 = AuthClienteDTO.builder()
                .username("other")
                .senha("otherpass")
                .build();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, new Object());

        // Reflexividade
        assertEquals(dto1, dto1);

        // Simetria
        assertTrue(dto1.equals(dto2) && dto2.equals(dto1));

        // Consistência
        assertEquals(dto1.equals(dto2), dto1.equals(dto2));
    }

    @Test
    void testToString() {
        AuthClienteDTO dto = AuthClienteDTO.builder()
                .username("usuario")
                .senha("senha")
                .build();

        String str = dto.toString();
        assertTrue(str.contains("username=usuario"));
        assertTrue(str.contains("senha=senha"));
    }

    @Test
    void testBuilderWithPartialFields() {
        AuthClienteDTO dto = AuthClienteDTO.builder()
                .username("onlyUsername")
                .build();

        assertEquals("onlyUsername", dto.getUsername());
        assertNull(dto.getSenha());

        AuthClienteDTO dto2 = AuthClienteDTO.builder()
                .senha("onlySenha")
                .build();

        assertNull(dto2.getUsername());
        assertEquals("onlySenha", dto2.getSenha());
    }
}
