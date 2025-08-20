package org.ecommerce.ecommerceapi.modules.client.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileClientResponseDTOTest {

    private ProfileClientResponseDTO make(String nome, String user, String email) {
        return ProfileClientResponseDTO.builder()
                .name(nome)
                .username(user)
                .email(email)
                .phone("11987654321")
                .address("Av x")
                .city("Cidade y")
                .state("sao paulo")
                .cep("06789123")
                .build();
    }

    @Test
    void equals_hashCode_allBranches() {
        ProfileClientResponseDTO a = make("User", "user", "user@u.com");

        assertEquals(a, a);
        assertNotEquals(a, null);
        assertNotEquals(a, "String");

        ProfileClientResponseDTO b = make("User", "user", "user@u.com");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        ProfileClientResponseDTO diffNome = make("UserX", "user", "user@u.com");
        assertNotEquals(a, diffNome);

        ProfileClientResponseDTO diffCidade = make("User", "user", "user@u.com");
        diffCidade.setCity("SP");
        assertNotEquals(a, diffCidade);
    }

    @Test
    void toString_containsAllFields() {
        ProfileClientResponseDTO dto = make("Edu", "edu", "edu@e.com");
        String s = dto.toString();
        assertTrue(s.contains("ProfileClientResponseDTO"));
        assertTrue(s.contains("name=Edu"));
        assertTrue(s.contains("username=edu"));
        assertTrue(s.contains("email=edu@e.com"));
        assertTrue(s.contains("phone=11987654321"));
        assertTrue(s.contains("address=Av x"));
        assertTrue(s.contains("city=Cidade y"));
        assertTrue(s.contains("state=sao paulo"));
        assertTrue(s.contains("cep=06789123"));
    }

    @Test
    void toString_containsAllMainFields() {
        ProfileClientResponseDTO dto = ProfileClientResponseDTO.builder()
                .name("Eduardo")
                .username("edu")
                .email("edu@e.com")
                .phone("11987654321")
                .address("Rua x")
                .city("Cidade y")
                .state("SP")
                .cep("06789123")
                .build();

        String s = dto.toString();

        assertTrue(s.startsWith("ProfileClientResponseDTO("), "toString nao comecou como esperado: " + s);
        assertTrue(s.contains("name=Eduardo"), s);
        assertTrue(s.contains("username=edu"), s);
        assertTrue(s.contains("email=edu@e.com"), s);
        assertTrue(s.contains("phone=11987654321"), s);
        assertTrue(s.contains("address=Rua x"), s);
        assertTrue(s.contains("city=Cidade y"), s);
        assertTrue(s.contains("state=SP"), s);
        assertTrue(s.contains("cep=06789123"), s);
    }

    @BeforeEach
    void setUp() {
        profileDTO = new ProfileClientResponseDTO();
        profileDTO.setName("João");
        profileDTO.setUsername("joao123");
        profileDTO.setEmail("joao@email.com");
        profileDTO.setPhone("123456789");
        profileDTO.setAddress("Rua A, 123");
        profileDTO.setCity("Cidade Exemplo");
        profileDTO.setState("Estado Exemplo");
        profileDTO.setCep("12345-678");
    }

    private ProfileClientResponseDTO profileDTO;

    @Test
    void testGettersAndSetters() {
        assertEquals("João", profileDTO.getName());
        assertEquals("joao123", profileDTO.getUsername());
        assertEquals("joao@email.com", profileDTO.getEmail());
        assertEquals("123456789", profileDTO.getPhone());
        assertEquals("Rua A, 123", profileDTO.getAddress());
        assertEquals("Cidade Exemplo", profileDTO.getCity());
        assertEquals("Estado Exemplo", profileDTO.getState());
        assertEquals("12345-678", profileDTO.getCep());
    }

    @Test
    void builder_toString_coversLombokInnerBuilder() {
        String s = ProfileClientResponseDTO.builder().toString();
        assertTrue(s.contains("ProfileClientResponseDTO.ProfileClientResponseDTOBuilder"), s);
    }

    @Test
    void testEqualsAndHashCode() {
        ProfileClientResponseDTO profileDTO2 = new ProfileClientResponseDTO();
        profileDTO2.setName("João");
        profileDTO2.setUsername("joao123");
        profileDTO2.setEmail("joao@email.com");
        profileDTO2.setPhone("123456789");
        profileDTO2.setAddress("Rua A, 123");
        profileDTO2.setCity("Cidade Exemplo");
        profileDTO2.setState("Estado Exemplo");
        profileDTO2.setCep("12345-678");

        assertEquals(profileDTO, profileDTO2);
        assertEquals(profileDTO.hashCode(), profileDTO2.hashCode());

        profileDTO2.setName("Maria");
        assertNotEquals(profileDTO, profileDTO2);
    }
}
