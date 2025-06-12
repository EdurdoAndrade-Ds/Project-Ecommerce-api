package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteClienteDTOTest {

    @Test
    void testBuilderAndEqualsHashCodeToString() {
        DeleteClienteDTO dto1 = DeleteClienteDTO.builder()
                .senha("123456")
                .build();

        DeleteClienteDTO dto2 = DeleteClienteDTO.builder()
                .senha("123456")
                .build();

        DeleteClienteDTO dto3 = DeleteClienteDTO.builder()
                .senha("outraSenha")
                .build();

        // equals e hashCode
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // toString
        assertNotNull(dto1.toString());
        assertTrue(dto1.toString().contains("123456"));

        // canEqual (é chamado dentro do equals, mas pode ser forçado também)
        assertTrue(dto1.canEqual(dto2));
    }
}
