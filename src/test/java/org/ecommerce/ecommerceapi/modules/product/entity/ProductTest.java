package org.ecommerce.ecommerceapi.modules.product.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testGettersAndSetters() {
        Product product = new Product();

        // Define valores
        product.setId(1L);
        product.setNome("Produto Teste");
        product.setDescricao("Descrição do Produto Teste");
        product.setPreco(BigDecimal.valueOf(99.99));
        product.setEstoque(10);
        product.setDescontoPercentual(15.0);
        product.setDescountedPrice(BigDecimal.valueOf(84.99));

        // Verifica valores atribuídos
        assertEquals(1L, product.getId());
        assertEquals("Produto Teste", product.getNome());
        assertEquals("Descrição do Produto Teste", product.getDescricao());
        assertEquals(BigDecimal.valueOf(99.99), product.getPreco());
        assertEquals(10, product.getEstoque());
        assertEquals(15.0, product.getDescontoPercentual());
        assertEquals(BigDecimal.valueOf(84.99), product.getDescountPrice());
    }

    @Test
    void testDefaultDiscountPercentage() {
        Product product = new Product();
        assertEquals(0.0, product.getDescontoPercentual());
    }
}
