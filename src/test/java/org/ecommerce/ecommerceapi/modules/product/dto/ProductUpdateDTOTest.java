package org.ecommerce.ecommerceapi.modules.product.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Objects; // Adicione esta importação

import static org.junit.jupiter.api.Assertions.*;

class ProductUpdateDTOTest {

    @Test
    void testEquals() {
        ProductUpdateDTO product1 = new ProductUpdateDTO();
        product1.setNome("Produto Teste");
        product1.setDescricao("Descrição do Produto Teste");
        product1.setPreco(BigDecimal.valueOf(99.99));

        ProductUpdateDTO product2 = new ProductUpdateDTO();
        product2.setNome("Produto Teste");
        product2.setDescricao("Descrição do Produto Teste");
        product2.setPreco(BigDecimal.valueOf(99.99));

        assertEquals(product1, product2); // Deve ser igual

        product2.setPreco(BigDecimal.valueOf(49.99));
        assertNotEquals(product1, product2); // Não deve ser igual
    }

    @Test
    void testHashCode() {
        ProductUpdateDTO product = new ProductUpdateDTO();
        product.setNome("Produto Teste");
        product.setDescricao("Descrição do Produto Teste");
        product.setPreco(BigDecimal.valueOf(99.99));

        int expectedHashCode = Objects.hash("Produto Teste", "Descrição do Produto Teste", BigDecimal.valueOf(99.99));
        assertEquals(expectedHashCode, product.hashCode());
    }

    @Test
    void testToString() {
        ProductUpdateDTO product = new ProductUpdateDTO();
        product.setNome("Produto Teste");
        product.setDescricao("Descrição do Produto Teste");
        product.setPreco(BigDecimal.valueOf(99.99));

        String expectedString = "ProductUpdateDTO{nome='Produto Teste', descricao='Descrição do Produto Teste', preco=99.99}";
        assertEquals(expectedString, product.toString());
    }

    @Test
    void testCanEqual() {
        ProductUpdateDTO productDTO = new ProductUpdateDTO();
        assertTrue(productDTO.canEqual(new ProductUpdateDTO())); // Deve retornar true
        assertFalse(productDTO.canEqual(new Object())); // Deve retornar false
    }
}
