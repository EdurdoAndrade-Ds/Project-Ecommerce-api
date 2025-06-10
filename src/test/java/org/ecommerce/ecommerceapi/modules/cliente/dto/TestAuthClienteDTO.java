package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestAuthClienteDTO {

    @Test
    void testGettersAndSetters() {
        AuthClienteDTO dto = new AuthClienteDTO();
        dto.setUsername("usuario");
        dto.setSenha("senha123");

        assertEquals("usuario", dto.getUsername());
        assertEquals("senha123", dto.getSenha());
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
    void testBuilder() {
        AuthClienteDTO dto = AuthClienteDTO.builder()
                .username("builderUser")
                .senha("builderPass")
                .build();

        assertEquals("builderUser", dto.getUsername());
        assertEquals("builderPass", dto.getSenha());
    }
}
