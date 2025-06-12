package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateClienteDTOTest {

    @Test
    void deveConstruirComBuilderEValidarCampos() {
        CreateClienteDTO dto = CreateClienteDTO.builder()
                .nome("Maria")
                .username("maria123")
                .email("maria@email.com")
                .senha("senhaSegura123")
                .telefone("11999999999")
                .endereco("Rua das Flores")
                .cidade("São Paulo")
                .estado("SP")
                .cep("01000-000")
                .build();

        assertEquals("Maria", dto.getNome());
        assertEquals("maria123", dto.getUsername());
        assertEquals("maria@email.com", dto.getEmail());
        assertEquals("senhaSegura123", dto.getSenha());
        assertEquals("11999999999", dto.getTelefone());
        assertEquals("Rua das Flores", dto.getEndereco());
        assertEquals("São Paulo", dto.getCidade());
        assertEquals("SP", dto.getEstado());
        assertEquals("01000-000", dto.getCep());
    }

    @Test
    void deveCompararObjetosIguais() {
        CreateClienteDTO dto1 = CreateClienteDTO.builder()
                .nome("João")
                .username("joao")
                .email("joao@email.com")
                .senha("12345678")
                .build();

        CreateClienteDTO dto2 = CreateClienteDTO.builder()
                .nome("João")
                .username("joao")
                .email("joao@email.com")
                .senha("12345678")
                .build();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void deveGerarToString() {
        CreateClienteDTO dto = CreateClienteDTO.builder()
                .nome("Test")
                .username("testuser")
                .email("test@test.com")
                .senha("abcdefghi")
                .build();

        String str = dto.toString();
        assertTrue(str.contains("testuser"));
        assertTrue(str.contains("test@test.com"));
    }
}