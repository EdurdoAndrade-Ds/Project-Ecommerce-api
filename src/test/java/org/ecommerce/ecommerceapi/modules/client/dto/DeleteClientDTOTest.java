package org.ecommerce.ecommerceapi.modules.client.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteClientDTOTest {

    @Test
    void equals_hashCode_allBranches() {
        DeleteClientDTO dto1 = new DeleteClientDTO("senha123");

        assertEquals(dto1, dto1);

        assertNotEquals(dto1, null);

        assertNotEquals(dto1, "string");

        DeleteClientDTO dto2 = new DeleteClientDTO("senha123");
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        DeleteClientDTO dto3 = new DeleteClientDTO("senha456");
        assertNotEquals(dto1, dto3);

        DeleteClientDTO dto4 = new DeleteClientDTO(null);
        assertNotEquals(dto1, dto4);

        DeleteClientDTO dto5 = new DeleteClientDTO(null);
        DeleteClientDTO dto6 = new DeleteClientDTO(null);
        assertEquals(dto5, dto6);
        assertEquals(dto5.hashCode(), dto6.hashCode());

    }


    @Test
    void testSetAndGetSenha() {
        DeleteClientDTO dto = new DeleteClientDTO();
        dto.setPassword("minhaSenha");
        assertEquals("minhaSenha", dto.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        DeleteClientDTO dto1 = new DeleteClientDTO("senha123");
        DeleteClientDTO dto2 = new DeleteClientDTO("senha123");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        DeleteClientDTO dto3 = new DeleteClientDTO("senha456");
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testToString() {
        DeleteClientDTO dto = new DeleteClientDTO("senha123");
        String str = dto.toString();
        assertTrue(str.contains("senha='senha123'"));
    }

    @Test
    void testBuilder() {
        DeleteClientDTO dto = DeleteClientDTO.builder()
                .password("senhaBuilder")
                .build();

        assertEquals("senhaBuilder", dto.getPassword());
    }
}
