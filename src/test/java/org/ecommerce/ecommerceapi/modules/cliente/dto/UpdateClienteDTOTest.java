package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateClienteDTOTest {

    @Test
    void deveConstruirComBuilderEValidarCampos() {
        UpdateClienteDTO dto = UpdateClienteDTO.builder()
                .nome("Carlos")
                .username("carlos123")
                .email("carlos@email.com")
                .telefone("1122334455")
                .endereco("Av. Central")
                .cidade("Curitiba")
                .estado("PR")
                .cep("80000-000")
                .build();

        assertEquals("Carlos", dto.getNome());
        assertEquals("carlos123", dto.getUsername());
        assertEquals("carlos@email.com", dto.getEmail());
        assertEquals("1122334455", dto.getTelefone());
        assertEquals("Av. Central", dto.getEndereco());
        assertEquals("Curitiba", dto.getCidade());
        assertEquals("PR", dto.getEstado());
        assertEquals("80000-000", dto.getCep());
    }

    @Test
    void deveCompararObjetosIguais() {
        UpdateClienteDTO dto1 = UpdateClienteDTO.builder()
                .username("abc")
                .email("a@a.com")
                .build();

        UpdateClienteDTO dto2 = UpdateClienteDTO.builder()
                .username("abc")
                .email("a@a.com")
                .build();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void deveGerarToStringComCampos() {
        UpdateClienteDTO dto = UpdateClienteDTO.builder()
                .nome("Lucas")
                .cidade("Natal")
                .build();

        String texto = dto.toString();
        assertTrue(texto.contains("Lucas"));
        assertTrue(texto.contains("Natal"));
    }
}