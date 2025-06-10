package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestProfileClienteResponseDTO {

    @Test
    void testGettersAndSetters() {
        ProfileClienteResponseDTO dto = new ProfileClienteResponseDTO();
        dto.setNome("Ana");
        dto.setUsername("ana123");
        dto.setEmail("ana@example.com");
        dto.setTelefone("555-1234");
        dto.setEndereco("Rua das Flores, 123");
        dto.setCidade("São Paulo");
        dto.setEstado("SP");
        dto.setCep("12345-678");

        assertEquals("Ana", dto.getNome());
        assertEquals("ana123", dto.getUsername());
        assertEquals("ana@example.com", dto.getEmail());
        assertEquals("555-1234", dto.getTelefone());
        assertEquals("Rua das Flores, 123", dto.getEndereco());
        assertEquals("São Paulo", dto.getCidade());
        assertEquals("SP", dto.getEstado());
        assertEquals("12345-678", dto.getCep());
    }

    @Test
    void testEqualsAndHashCode() {
        ProfileClienteResponseDTO dto1 = ProfileClienteResponseDTO.builder()
                .nome("Ana")
                .username("ana123")
                .email("ana@example.com")
                .telefone("555-1234")
                .endereco("Rua das Flores, 123")
                .cidade("São Paulo")
                .estado("SP")
                .cep("12345-678")
                .build();

        ProfileClienteResponseDTO dto2 = ProfileClienteResponseDTO.builder()
                .nome("Ana")
                .username("ana123")
                .email("ana@example.com")
                .telefone("555-1234")
                .endereco("Rua das Flores, 123")
                .cidade("São Paulo")
                .estado("SP")
                .cep("12345-678")
                .build();

        ProfileClienteResponseDTO dto3 = ProfileClienteResponseDTO.builder()
                .nome("Carlos")
                .username("carlos456")
                .email("carlos@example.com")
                .telefone("555-5678")
                .endereco("Av. Brasil, 456")
                .cidade("Rio de Janeiro")
                .estado("RJ")
                .cep("98765-432")
                .build();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testToString() {
        ProfileClienteResponseDTO dto = ProfileClienteResponseDTO.builder()
                .nome("Ana")
                .username("ana123")
                .email("ana@example.com")
                .telefone("555-1234")
                .endereco("Rua das Flores, 123")
                .cidade("São Paulo")
                .estado("SP")
                .cep("12345-678")
                .build();

        String toStringValue = dto.toString();

        assertTrue(toStringValue.contains("nome=Ana"));
        assertTrue(toStringValue.contains("username=ana123"));
        assertTrue(toStringValue.contains("email=ana@example.com"));
        assertTrue(toStringValue.contains("telefone=555-1234"));
        assertTrue(toStringValue.contains("endereco=Rua das Flores, 123"));
        assertTrue(toStringValue.contains("cidade=São Paulo"));
        assertTrue(toStringValue.contains("estado=SP"));
        assertTrue(toStringValue.contains("cep=12345-678"));
    }

    @Test
    void testBuilder() {
        ProfileClienteResponseDTO dto = ProfileClienteResponseDTO.builder()
                .nome("Ana")
                .username("ana123")
                .email("ana@example.com")
                .telefone("555-1234")
                .endereco("Rua das Flores, 123")
                .cidade("São Paulo")
                .estado("SP")
                .cep("12345-678")
                .build();

        assertEquals("Ana", dto.getNome());
        assertEquals("ana123", dto.getUsername());
        assertEquals("ana@example.com", dto.getEmail());
        assertEquals("555-1234", dto.getTelefone());
        assertEquals("Rua das Flores, 123", dto.getEndereco());
        assertEquals("São Paulo", dto.getCidade());
        assertEquals("SP", dto.getEstado());
        assertEquals("12345-678", dto.getCep());
    }
}
