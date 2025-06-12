package org.ecommerce.ecommerceapi.modules.product.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductResponseDTOTest {

    @Test
    void testGettersAndSetters() {
        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setId(1L);
        dto.setNome("Produto Teste");
        dto.setDescricao("Descrição do Produto Teste");
        dto.setPreco(new BigDecimal("25.00"));
        dto.setEstoque(10);

        assertEquals(1L, dto.getId());
        assertEquals("Produto Teste", dto.getNome());
        assertEquals("Descrição do Produto Teste", dto.getDescricao());
        assertEquals(new BigDecimal("25.00"), dto.getPreco());
        assertEquals(10, dto.getEstoque());
    }

    @Test
    void testEqualsAndHashCode() {
        ProductResponseDTO dto1 = new ProductResponseDTO();
        dto1.setId(1L);
        dto1.setNome("Produto Teste");
        dto1.setDescricao("Descrição do Produto Teste");
        dto1.setPreco(new BigDecimal("25.00"));
        dto1.setEstoque(10);

        ProductResponseDTO dto2 = new ProductResponseDTO();
        dto2.setId(1L);
        dto2.setNome("Produto Teste");
        dto2.setDescricao("Descrição do Produto Teste");
        dto2.setPreco(new BigDecimal("25.00"));
        dto2.setEstoque(10);

        ProductResponseDTO dto3 = new ProductResponseDTO();
        dto3.setId(2L);
        dto3.setNome("Produto Diferente");
        dto3.setDescricao("Descrição do Produto Diferente");
        dto3.setPreco(new BigDecimal("30.00"));
        dto3.setEstoque(5);

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testToString() {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(1L);
        dto.setNome("Produto Teste");
        dto.setDescricao("Descrição do Produto Teste");
        dto.setPreco(new BigDecimal("25.00"));
        dto.setEstoque(10);

        String expectedString = "ProductResponseDTO{id=1, nome='Produto Teste', descricao='Descrição do Produto Teste', preco=25.00, estoque=10}";
        assertEquals(expectedString, dto.toString());
    }

    @Test
    void testNullValues() {
        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setId(null);
        dto.setNome(null);
        dto.setDescricao(null);
        dto.setPreco(null);
        dto.setEstoque(null);

        assertNull(dto.getId());
        assertNull(dto.getNome());
        assertNull(dto.getDescricao());
        assertNull(dto.getPreco());
        assertNull(dto.getEstoque());
    }

    @Test
    void testCanEqual() {
        ProductResponseDTO dto = new ProductResponseDTO();
        assertTrue(dto.canEqual(new ProductResponseDTO()));
        assertFalse(dto.canEqual(new Object())); // Testa com um objeto de tipo diferente
    }
}
