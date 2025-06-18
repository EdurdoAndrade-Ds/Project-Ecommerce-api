package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileClienteResponseDTOTest {

    private ProfileClienteResponseDTO profileDTO;

    @BeforeEach
    void setUp() {
        profileDTO = new ProfileClienteResponseDTO();
        profileDTO.setNome("João");
        profileDTO.setUsername("joao123");
        profileDTO.setEmail("joao@email.com");
        profileDTO.setTelefone("123456789");
        profileDTO.setEndereco("Rua A, 123");
        profileDTO.setCidade("Cidade Exemplo");
        profileDTO.setEstado("Estado Exemplo");
        profileDTO.setCep("12345-678");
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("João", profileDTO.getNome());
        assertEquals("joao123", profileDTO.getUsername());
        assertEquals("joao@email.com", profileDTO.getEmail());
        assertEquals("123456789", profileDTO.getTelefone());
        assertEquals("Rua A, 123", profileDTO.getEndereco());
        assertEquals("Cidade Exemplo", profileDTO.getCidade());
        assertEquals("Estado Exemplo", profileDTO.getEstado());
        assertEquals("12345-678", profileDTO.getCep());
    }

    @Test
    void testEqualsAndHashCode() {
        ProfileClienteResponseDTO profileDTO2 = new ProfileClienteResponseDTO();
        profileDTO2.setNome("João");
        profileDTO2.setUsername("joao123");
        profileDTO2.setEmail("joao@email.com");
        profileDTO2.setTelefone("123456789");
        profileDTO2.setEndereco("Rua A, 123");
        profileDTO2.setCidade("Cidade Exemplo");
        profileDTO2.setEstado("Estado Exemplo");
        profileDTO2.setCep("12345-678");

        assertEquals(profileDTO, profileDTO2);
        assertEquals(profileDTO.hashCode(), profileDTO2.hashCode());

        profileDTO2.setNome("Maria"); // Alterando para testar desigualdade
        assertNotEquals(profileDTO, profileDTO2);
    }

    @Test
    void testEqualsWithDifferentObjects() {
        ProfileClienteResponseDTO profileDTO2 = new ProfileClienteResponseDTO();
        profileDTO2.setNome("Maria"); // Nome diferente

        assertNotEquals(profileDTO, profileDTO2);
    }

    @Test
    void testEqualsWithNull() {
        assertNotEquals(profileDTO, null);
    }

    @Test
    void testEqualsWithDifferentClass() {
        String differentClassObject = "This is a string";
        assertNotEquals(profileDTO, differentClassObject);
    }
}
