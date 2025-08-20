package org.ecommerce.ecommerceapi.modules.client.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthClientDTOTest {

    private AuthClientDTO make(String username, String password) {
        AuthClientDTO dto = new AuthClientDTO();
        dto.setUsername(username);
        dto.setPassword(password);
        return dto;
    }

    @Test
    void equals_hashCode_fullBranches() {
        AuthClientDTO base = make("user", "123");

        assertEquals(base, base);

        AuthClientDTO igual = make(new String("user"), new String("123"));
        assertEquals(base, igual);
        assertEquals(base.hashCode(), igual.hashCode());

        assertNotEquals(base, null);
        assertNotEquals(base, "string");

        AuthClientDTO diffUsername = make("user2", "123");
        assertNotEquals(base, diffUsername);

        AuthClientDTO diffPassword = make("user", "999");
        assertNotEquals(base, diffPassword);

        assertNotEquals(make(null, "123"), make("user", "123"));
        assertNotEquals(make("user", null), make("user", "123"));

        assertEquals(make(null, "123"), make(null, "123"));
        assertEquals(make("user", null), make("user", null));

        assertTrue(base.canEqual(igual));
        assertFalse(base.canEqual("string"));
        assertTrue(base.toString().contains("AuthClientDTO"));

        AuthClientDTO viaBuilder = AuthClientDTO.builder()
                .username("user")
                .password("123")
                .build();
        assertEquals(base, viaBuilder);
    }

    @Test
    void testHashCodeWithNulls() {

        AuthClientDTO ambosNull = make(null, null);
        ambosNull.hashCode();

        AuthClientDTO usernameNull = make(null, "123");
        usernameNull.hashCode();

        AuthClientDTO PasswordNull  = make("user", null);
        PasswordNull.hashCode();
    }

    @Test
    void testGettersAndSetters() {
        AuthClientDTO dto = new AuthClientDTO();
        dto.setUsername("usuario");
        dto.setPassword("password123");

        assertEquals("usuario", dto.getUsername());
        assertEquals("password123", dto.getPassword());

        // Testando setters com null
        dto.setUsername(null);
        dto.setPassword(null);
        assertNull(dto.getUsername());
        assertNull(dto.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        AuthClientDTO dto1 = AuthClientDTO.builder()
                .username("user")
                .password("pass")
                .build();

        AuthClientDTO dto2 = AuthClientDTO.builder()
                .username("user")
                .password("pass")
                .build();

        AuthClientDTO dto3 = AuthClientDTO.builder()
                .username("other")
                .password("otherpassword")
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
        AuthClientDTO dto = AuthClientDTO.builder()
                .username("usuario")
                .password("password")
                .build();

        String str = dto.toString();
        assertTrue(str.contains("username=usuario"));
        assertTrue(str.contains("password=password"));
    }

    @Test
    void testBuilderWithPartialFields() {
        AuthClientDTO dto = AuthClientDTO.builder()
                .username("onlyUsername")
                .build();

        assertEquals("onlyUsername", dto.getUsername());
        assertNull(dto.getPassword());

        AuthClientDTO dto2 = AuthClientDTO.builder()
                .password("onlyPassword")
                .build();

        assertNull(dto2.getUsername());
        assertEquals("onlyPassword", dto2.getPassword());
    }
}
