package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestAuthClienteResponseDTO {

    @Test
    void testGettersAndSetters() {
        AuthClienteResponseDTO dto = new AuthClienteResponseDTO();
        dto.setToken("token123");
        dto.setId(100L);
        dto.setUsername("usuario1");

        assertEquals("token123", dto.getToken());
        assertEquals(100L, dto.getId());
        assertEquals("usuario1", dto.getUsername());
    }

    @Test
    void testEqualsAndHashCode() {
        AuthClienteResponseDTO dto1 = AuthClienteResponseDTO.builder()
                .token("token123")
                .id(100L)
                .username("usuario1")
                .build();

        AuthClienteResponseDTO dto2 = AuthClienteResponseDTO.builder()
                .token("token123")
                .id(100L)
                .username("usuario1")
                .build();

        AuthClienteResponseDTO dto3 = AuthClienteResponseDTO.builder()
                .token("tokenXYZ")
                .id(101L)
                .username("usuario2")
                .build();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testToString() {
        AuthClienteResponseDTO dto = AuthClienteResponseDTO.builder()
                .token("tokenABC")
                .id(55L)
                .username("userABC")
                .build();

        String str = dto.toString();
        assertTrue(str.contains("token=tokenABC"));
        assertTrue(str.contains("id=55"));
        assertTrue(str.contains("username=userABC"));
    }

    @Test
    void testBuilder() {
        AuthClienteResponseDTO dto = AuthClienteResponseDTO.builder()
                .token("builderToken")
                .id(999L)
                .username("builderUser")
                .build();

        assertEquals("builderToken", dto.getToken());
        assertEquals(999L, dto.getId());
        assertEquals("builderUser", dto.getUsername());
    }
}
