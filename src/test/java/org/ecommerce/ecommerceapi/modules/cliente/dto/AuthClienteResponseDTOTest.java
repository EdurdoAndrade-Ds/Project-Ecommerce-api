package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthClienteResponseDTOTest {

    private AuthClienteResponseDTO authDTO;

    @BeforeEach
    void setUp() {
        authDTO = new AuthClienteResponseDTO();
        authDTO.setToken("token123");
        authDTO.setId(1L);
        authDTO.setUsername("joao123");
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("token123", authDTO.getToken());
        assertEquals(1L, authDTO.getId());
        assertEquals("joao123", authDTO.getUsername());
    }

    @Test
    void testEqualsAndHashCode() {
        AuthClienteResponseDTO authDTO2 = new AuthClienteResponseDTO();
        authDTO2.setToken("token123");
        authDTO2.setId(1L);
        authDTO2.setUsername("joao123");

        assertEquals(authDTO, authDTO2);
        assertEquals(authDTO.hashCode(), authDTO2.hashCode());

        authDTO2.setToken("token456"); // Alterando para testar desigualdade
        assertNotEquals(authDTO, authDTO2);
    }

    @Test
    void testEqualsWithDifferentObjects() {
        AuthClienteResponseDTO authDTO2 = new AuthClienteResponseDTO();
        authDTO2.setId(2L); // ID diferente

        assertNotEquals(authDTO, authDTO2);
    }

    @Test
    void testEqualsWithNull() {
        assertNotEquals(authDTO, null);
    }

    @Test
    void testEqualsWithDifferentClass() {
        String differentClassObject = "This is a string";
        assertNotEquals(authDTO, differentClassObject);
    }
}
