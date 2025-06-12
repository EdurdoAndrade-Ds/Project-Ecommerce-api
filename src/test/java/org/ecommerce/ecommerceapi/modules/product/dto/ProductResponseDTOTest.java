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

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
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
    public void testProductResponseDTOGettersAndSetters() {
        ProductResponseDTO productResponse = new ProductResponseDTO();
        
        // Testar o setter e getter para o ID
        productResponse.setId(1L);
        assertEquals(1L, productResponse.getId());

        // Testar o setter e getter para o nome
        productResponse.setNome("Product A");
        assertEquals("Product A", productResponse.getNome());

        // Testar o setter e getter para o preço usando BigDecimal
        productResponse.setPreco(new BigDecimal("99.99")); 
        assertEquals(new BigDecimal("99.99"), productResponse.getPreco());

        // Continue para outros atributos conforme necessário
    }
    
}
