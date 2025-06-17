package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateClienteDTOTest {

    @Test
    void testEqualsHashCodeToStringAndCanEqual() {
        UpdateClienteDTO dto1 = UpdateClienteDTO.builder()
                .nome("João")
                .username("joao123")
                .email("joao@email.com")
                .telefone("123456789")
                .endereco("Rua A")
                .cidade("Cidade")
                .estado("SP")
                .cep("00000-000")
                .build();

        UpdateClienteDTO dto2 = UpdateClienteDTO.builder()
                .nome("João")
                .username("joao123")
                .email("joao@email.com")
                .telefone("123456789")
                .endereco("Rua A")
                .cidade("Cidade")
                .estado("SP")
                .cep("00000-000")
                .build();

        UpdateClienteDTO dto3 = UpdateClienteDTO.builder()
                .nome("Maria")
                .build();

        // equals e hashCode
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // toString
        assertNotNull(dto1.toString());
        assertTrue(dto1.toString().contains("João"));

        // canEqual (indiretamente chamado por equals, mas garantido aqui)
        assertTrue(dto1.canEqual(dto2));
    }

    @Test
    void deveSetarEmailCorretamente() {
        UpdateClienteDTO dto = new UpdateClienteDTO();
        dto.setEmail("novoemail@email.com");
        assertEquals("novoemail@email.com", dto.getEmail());
    }

    @Test
    void deveSetarUsernameCorretamente() {
        UpdateClienteDTO dto = new UpdateClienteDTO();
        dto.setUsername("novoUsername");
        assertEquals("novoUsername", dto.getUsername());
    }
}
