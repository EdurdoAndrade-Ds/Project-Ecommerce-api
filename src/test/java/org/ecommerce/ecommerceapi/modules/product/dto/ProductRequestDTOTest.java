package org.ecommerce.ecommerceapi.modules.product.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductRequestDTOTest {

    @Test
    void testGettersAndSetters() {
        ProductRequestDTO dto = new ProductRequestDTO();

        dto.setNome("Produto Teste");
        dto.setDescricao("Descrição do Produto Teste");
        dto.setPreco(new BigDecimal("25.00"));
        dto.setEstoque(10);

        assertEquals("Produto Teste", dto.getNome());
        assertEquals("Descrição do Produto Teste", dto.getDescricao());
        assertEquals(new BigDecimal("25.00"), dto.getPreco());
        assertEquals(10, dto.getEstoque());
    }

    @Test
    void testEqualsAndHashCode() {
        ProductRequestDTO dto1 = createProductRequestDTO("Produto Teste", "Descrição do Produto Teste", new BigDecimal("25.00"), 10);
        ProductRequestDTO dto2 = createProductRequestDTO("Produto Teste", "Descrição do Produto Teste", new BigDecimal("25.00"), 10);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        ProductRequestDTO dto = createProductRequestDTO("Produto Teste", "Descrição do Produto Teste", new BigDecimal("25.00"), 10);

        String expectedString = "ProductRequestDTO{nome='Produto Teste', descricao='Descrição do Produto Teste', preco=25.00, estoque=10}";
        assertEquals(expectedString, dto.toString());
    }

    @Test
    void testNullValues() {
        ProductRequestDTO dto = new ProductRequestDTO();

        dto.setNome(null);
        dto.setDescricao(null);
        dto.setPreco(null);
        dto.setEstoque(null);

        assertNull(dto.getNome());
        assertNull(dto.getDescricao());
        assertNull(dto.getPreco());
        assertNull(dto.getEstoque());
    }

    @Test
    void testEqualsSameObject() {
        ProductRequestDTO product = new ProductRequestDTO();
        assertTrue(product.equals(product));
    }

    @Test
    void testEqualsDifferentType() {
        ProductRequestDTO product = new ProductRequestDTO();
        assertFalse(product.equals("Not a ProductRequestDTO"));
    }

    @Test
    void testEqualsDifferentObjectsSameValues() {
        ProductRequestDTO product1 = createProductRequestDTO("Produto A", "Descrição A", new BigDecimal("10.00"), 100);
        ProductRequestDTO product2 = createProductRequestDTO("Produto A", "Descrição A", new BigDecimal("10.00"), 100);
        
        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    void testEqualsDifferentObjectsDifferentValues() {
        ProductRequestDTO product1 = createProductRequestDTO("Produto A", "Descrição A", new BigDecimal("10.00"), 100);
        ProductRequestDTO product2 = createProductRequestDTO("Produto B", "Descrição B", new BigDecimal("20.00"), 200);
        
        assertNotEquals(product1, product2);
        assertNotEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    void testEqualsWithNullFields() {
        ProductRequestDTO product1 = new ProductRequestDTO();
        product1.setNome(null);
        product1.setDescricao("Descrição A");
        product1.setPreco(new BigDecimal("10.00"));
        product1.setEstoque(100);

        ProductRequestDTO product2 = new ProductRequestDTO();
        product2.setNome(null);
        product2.setDescricao("Descrição A");
        product2.setPreco(new BigDecimal("10.00"));
        product2.setEstoque(100);

        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());
    }

    // Helper method for creating ProductRequestDTO instances
    private ProductRequestDTO createProductRequestDTO(String nome, String descricao, BigDecimal preco, Integer estoque) {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setNome(nome);
        dto.setDescricao(descricao);
        dto.setPreco(preco);
        dto.setEstoque(estoque);
        return dto;
    }
}
