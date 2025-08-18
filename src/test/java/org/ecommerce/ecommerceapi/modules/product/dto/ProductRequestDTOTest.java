package org.ecommerce.ecommerceapi.modules.product.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Objects; // Adicione esta importação

import static org.junit.jupiter.api.Assertions.*;

class ProductRequestDTOTest {

    @Test
    void testEquals() {
        ProductRequestDTO product1 = new ProductRequestDTO();
        product1.setNome("Produto Teste");
        product1.setDescricao("Descrição do Produto Teste");
        product1.setPreco(BigDecimal.valueOf(99.99));
        product1.setEstoque(10);

        ProductRequestDTO product2 = new ProductRequestDTO();
        product2.setNome("Produto Teste");
        product2.setDescricao("Descrição do Produto Teste");
        product2.setPreco(BigDecimal.valueOf(99.99));
        product2.setEstoque(10);

        assertEquals(product1, product2); // Deve ser igual

        product2.setEstoque(5);
        assertNotEquals(product1, product2); // Não deve ser igual
    }

    @Test
    void testHashCode() {
        ProductRequestDTO product = new ProductRequestDTO();
        product.setNome("Produto Teste");
        product.setDescricao("Descrição do Produto Teste");
        product.setPreco(BigDecimal.valueOf(99.99));
        product.setEstoque(10);

        int expectedHashCode = Objects.hash("Produto Teste", "Descrição do Produto Teste", BigDecimal.valueOf(99.99), 10);
        assertEquals(expectedHashCode, product.hashCode());
    }

    @Test
    void testToString() {
        ProductRequestDTO product = new ProductRequestDTO();
        product.setNome("Produto Teste");
        product.setDescricao("Descrição do Produto Teste");
        product.setPreco(BigDecimal.valueOf(99.99));
        product.setEstoque(10);

        String expectedString = "ProductRequestDTO{name='Produto Teste', descricao='Descrição do Produto Teste', price=99.99, stock=10}";
        assertEquals(expectedString, product.toString());
    }

    @Test
    void testCanEqual() {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        assertTrue(productRequestDTO.canEqual(new ProductRequestDTO())); // Deve retornar true
        assertFalse(productRequestDTO.canEqual(new Object())); // Deve retornar false
    }
}
