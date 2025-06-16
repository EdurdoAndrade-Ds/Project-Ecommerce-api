package org.ecommerce.ecommerceapi.modules.product.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductResponseDTOTest {

    @Test
    void testGettersAndSetters() {
        ProductResponseDTO productResponse = new ProductResponseDTO();

        // Define valores
        productResponse.setId(1L);
        productResponse.setNome("Produto Teste");
        productResponse.setDescricao("Descrição do Produto Teste");
        productResponse.setPreco(BigDecimal.valueOf(99.99));
        productResponse.setEstoque(10);
        productResponse.setDescountPercentage(15.0);
        productResponse.setDescountedPrice(BigDecimal.valueOf(84.99));

        // Verifica valores atribuídos
        assertEquals(1L, productResponse.getId());
        assertEquals("Produto Teste", productResponse.getNome());
        assertEquals("Descrição do Produto Teste", productResponse.getDescricao());
        assertEquals(BigDecimal.valueOf(99.99), productResponse.getPreco());
        assertEquals(10, productResponse.getEstoque());
        assertEquals(15.0, productResponse.getDescountPercentage());
        assertEquals(BigDecimal.valueOf(84.99), productResponse.getDescountedPrice());
    }

    @Test
    void testEqualsAndHashCode() {
        ProductResponseDTO product1 = new ProductResponseDTO();
        ProductResponseDTO product2 = new ProductResponseDTO();

        product1.setId(1L);
        product1.setNome("Produto Teste");
        product1.setDescricao("Descrição do Produto Teste");
        product1.setPreco(BigDecimal.valueOf(99.99));
        product1.setEstoque(10);

        product2.setId(1L);
        product2.setNome("Produto Teste");
        product2.setDescricao("Descrição do Produto Teste");
        product2.setPreco(BigDecimal.valueOf(99.99));
        product2.setEstoque(10);

        // Testa que os produtos são iguais
        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());

        // Modifica um produto e testa que eles não são mais iguais
        product2.setEstoque(5);
        assertNotEquals(product1, product2);
    }

    @Test
    void testToString() {
        ProductResponseDTO productResponse = new ProductResponseDTO();
        productResponse.setId(1L);
        productResponse.setNome("Produto Teste");
        productResponse.setDescricao("Descrição do Produto Teste");
        productResponse.setPreco(BigDecimal.valueOf(99.99));
        productResponse.setEstoque(10);

        String expectedString = "ProductResponseDTO{" +
                "id=1, " +
                "nome='Produto Teste', " +
                "descricao='Descrição do Produto Teste', " +
                "preco=99.99, " +
                "estoque=10" +
                '}';
        assertEquals(expectedString, productResponse.toString());
    }
    @Test
    void testCanEqual() {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        assertTrue(productResponseDTO.canEqual(new ProductResponseDTO())); // Deve retornar true
        assertFalse(productResponseDTO.canEqual(new Object())); // Deve retornar false
    }
}