package org.ecommerce.ecommerceapi.modules.product.entities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    void testProduct() {
        // Criação de uma instância de Product
        Product product = new Product();
        
        // Definindo valores
        product.setId(1L);
        product.setNome("Produto Teste");
        product.setDescricao("Descrição do Produto Teste");
        product.setPreco(new BigDecimal("25.00"));
        product.setEstoque(10);

        // Verificando se os valores estão corretos
        assertEquals(1L, product.getId());
        assertEquals("Produto Teste", product.getNome());
        assertEquals("Descrição do Produto Teste", product.getDescricao());
        assertEquals(new BigDecimal("25.00"), product.getPreco());
        assertEquals(10, product.getEstoque());
    }
}
