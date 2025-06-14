package org.ecommerce.ecommerceapi.modules.product.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductRequestDTOTest {

    @Test
    void testGettersAndSetters() {
        ProductRequestDTO productRequest = new ProductRequestDTO();

        // Define valores
        productRequest.setNome("Produto Teste");
        productRequest.setDescricao("Descrição do Produto Teste");
        productRequest.setPreco(BigDecimal.valueOf(99.99));
        productRequest.setEstoque(10);

        // Verifica valores atribuídos
        assertEquals("Produto Teste", productRequest.getNome());
        assertEquals("Descrição do Produto Teste", productRequest.getDescricao());
        assertEquals(BigDecimal.valueOf(99.99), productRequest.getPreco());
        assertEquals(10, productRequest.getEstoque());
    }

    @Test
    void testEqualsAndHashCode() {
        ProductRequestDTO product1 = new ProductRequestDTO();
        ProductRequestDTO product2 = new ProductRequestDTO();

        product1.setNome("Produto Teste");
        product1.setDescricao("Descrição do Produto Teste");
        product1.setPreco(BigDecimal.valueOf(99.99));
        product1.setEstoque(10);

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
        ProductRequestDTO productRequest = new ProductRequestDTO();
        productRequest.setNome("Produto Teste");
        productRequest.setDescricao("Descrição do Produto Teste");
        productRequest.setPreco(BigDecimal.valueOf(99.99));
        productRequest.setEstoque(10);

        String expectedString = "ProductRequestDTO{" +
                "nome='Produto Teste', " +
                "descricao='Descrição do Produto Teste', " +
                "preco=99.99, " +
                "estoque=10" +
                '}';
        assertEquals(expectedString, productRequest.toString());
    }
}
