package org.ecommerce.ecommerceapi.modules.product.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductUpdateDTOTest {

    @Test
    void testGettersAndSetters() {
        ProductUpdateDTO productUpdate = new ProductUpdateDTO();

        // Define valores
        productUpdate.setNome("Produto Atualizado");
        productUpdate.setDescricao("Descrição do Produto Atualizado");
        productUpdate.setPreco(BigDecimal.valueOf(79.99));

        // Verifica valores atribuídos
        assertEquals("Produto Atualizado", productUpdate.getNome());
        assertEquals("Descrição do Produto Atualizado", productUpdate.getDescricao());
        assertEquals(BigDecimal.valueOf(79.99), productUpdate.getPreco());
    }

    @Test
    void testEqualsAndHashCode() {
        ProductUpdateDTO product1 = new ProductUpdateDTO();
        ProductUpdateDTO product2 = new ProductUpdateDTO();

        product1.setNome("Produto Atualizado");
        product1.setDescricao("Descrição do Produto Atualizado");
        product1.setPreco(BigDecimal.valueOf(79.99));

        product2.setNome("Produto Atualizado");
        product2.setDescricao("Descrição do Produto Atualizado");
        product2.setPreco(BigDecimal.valueOf(79.99));

        // Testa que os produtos são iguais
        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());

        // Modifica um produto e testa que eles não são mais iguais
        product2.setPreco(BigDecimal.valueOf(89.99));
        assertNotEquals(product1, product2);
    }

    @Test
    void testToString() {
        ProductUpdateDTO productUpdate = new ProductUpdateDTO();
        productUpdate.setNome("Produto Atualizado");
        productUpdate.setDescricao("Descrição do Produto Atualizado");
        productUpdate.setPreco(BigDecimal.valueOf(79.99));

        String expectedString = "ProductUpdateDTO{" +
                "nome='Produto Atualizado', " +
                "descricao='Descrição do Produto Atualizado', " +
                "preco=79.99" +
                '}';
        assertEquals(expectedString, productUpdate.toString());
    }
}
