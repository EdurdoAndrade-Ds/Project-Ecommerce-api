package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthClienteDTOTest {

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
