package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteClienteDTOTest {

    @Test
    void testSetAndGetSenha() {
        DeleteClienteDTO dto = new DeleteClienteDTO();
        dto.setSenha("minhaSenha");
        assertEquals("minhaSenha", dto.getSenha());
    }

    @Test
    void testEqualsAndHashCode() {
        DeleteClienteDTO dto1 = new DeleteClienteDTO("senha123");
        DeleteClienteDTO dto2 = new DeleteClienteDTO("senha123");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        DeleteClienteDTO dto3 = new DeleteClienteDTO("senha456");
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testToString() {
        DeleteClienteDTO dto = new DeleteClienteDTO("senha123");
        String str = dto.toString();
        assertTrue(str.contains("senha='senha123'"));
    }

    @Test
    void testBuilder() {
        DeleteClienteDTO dto = DeleteClienteDTO.builder()
                .senha("senhaBuilder")
                .build();

        assertEquals("senhaBuilder", dto.getSenha());
    }
}
