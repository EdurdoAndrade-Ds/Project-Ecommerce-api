package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.beans.BeanMap;

import static org.junit.jupiter.api.Assertions.*;

class ProfileClienteResponseDTOTest {

    private ProfileClienteResponseDTO make(String nome, String user, String email) {
        return ProfileClienteResponseDTO.builder()
                .nome(nome)
                .username(user)
                .email(email)
                .telefone("11987654321")
                .endereco("Av x")
                .cidade("Cidade y")
                .estado("sao paulo")
                .cep("06789123")
                .build();
    }

    @Test
    void equals_hashCode_allBranches() {
        ProfileClienteResponseDTO a = make("User", "user", "user@u.com");

        assertEquals(a, a);

        assertNotEquals(a, null);

        assertNotEquals(a, "String");

        ProfileClienteResponseDTO b = make("User", "user", "user@u.com");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        ProfileClienteResponseDTO diffNome = make("UserX", "user", "user@u.com");
        assertNotEquals(a, diffNome);

        ProfileClienteResponseDTO diffCidade = make("User", "user", "user@u.com");
        diffCidade.setCidade("SP");
        assertNotEquals(a, diffCidade);
    }

    @Test
    void toString_containsAllFields() {
        ProfileClienteResponseDTO dto = make("Edu", "edu", "edu@e.com");
        String s = dto.toString();
        assertTrue(s.contains("ProfileClienteResponseDTO"));
        assertTrue(s.contains("nome=Edu"));
        assertTrue(s.contains("username=edu"));
        assertTrue(s.contains("email=edu@e.com"));
        assertTrue(s.contains("telefone=11987654321"));
        assertTrue(s.contains("cidade=Cidade y"));
        assertTrue(s.contains("estado=sao paulo"));
        assertTrue(s.contains("cep=06789123"));
    }

    @Test
    void toString_containsAllMainFields() {
        ProfileClienteResponseDTO dto = new ProfileClienteResponseDTO().builder()
                .nome("Eduardo")
                .username("edu")
                .email("edu@e.com")
                .telefone("11987654321")
                .endereco("Rua x")
                .cidade("Cidade y")
                .estado("SP")
                .cep("06789123")
                .build();

        String s = dto.toString();

        assertTrue(s.startsWith("ProfileClienteResponseDTO("), "toStrinf nao comecou como esperado: " + s);
        assertTrue(s.contains("nome=Eduardo"), s);
        assertTrue(s.contains("username=edu"), s);
        assertTrue(s.contains("email=edu@e.com"), s);
        assertTrue(s.contains("telefone=11987654321"), s);
        assertTrue(s.contains("endereco=Rua x"), s);
        assertTrue(s.contains("cidade=Cidade y"), s);
        assertTrue(s.contains("estado=SP"), s);
        assertTrue(s.contains("cep=06789123"), s);
    }


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
    void builder_toString_coversLombokInnerBuilder() {
        String s = ProfileClienteResponseDTO.builder()
                .nome("X")
                .username("y")
                .email("z@z.com")
                .toString();

        assertTrue(s.contains("ProfileClienteResponseDTOBuilder"), s);
        assertTrue(s.contains("nome=X"), s);
        assertTrue(s.contains("username=y"), s);
        assertTrue(s.contains("email=z@z.com"), s);
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
