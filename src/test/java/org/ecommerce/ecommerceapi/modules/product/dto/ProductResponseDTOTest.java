package org.ecommerce.ecommerceapi.modules.product.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductResponseDTOTest {

    @Test
    void deveCriarComSettersEVerificarCampos() {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(1L);
        dto.setNome("Notebook");
        dto.setDescricao("Notebook Gamer");
        dto.setPreco(BigDecimal.valueOf(5999.99));
        dto.setEstoque(15);

        assertEquals(1L, dto.getId());
        assertEquals("Notebook", dto.getNome());
        assertEquals("Notebook Gamer", dto.getDescricao());
        assertEquals(BigDecimal.valueOf(5999.99), dto.getPreco());
        assertEquals(15, dto.getEstoque());
    }

    @Test
    void deveTestarToStringEqualsHashCode() {
        ProductResponseDTO dto1 = new ProductResponseDTO();
        dto1.setId(2L);
        dto1.setNome("Mouse");
        dto1.setDescricao("Mouse óptico");
        dto1.setPreco(BigDecimal.TEN);
        dto1.setEstoque(100);

        ProductResponseDTO dto2 = new ProductResponseDTO();
        dto2.setId(2L);
        dto2.setNome("Mouse");
        dto2.setDescricao("Mouse óptico");
        dto2.setPreco(BigDecimal.TEN);
        dto2.setEstoque(100);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertTrue(dto1.toString().contains("Mouse"));
    }
}